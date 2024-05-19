import React, { useState, useEffect } from 'react';
import { CartModel } from '../../Types/types';
import { SpinnerLoading } from '../Utils/SpinnerLoading';
import { NavLink } from "react-router-dom";

const Cart: React.FC = () => {
  const [cartItems, setCartItems] = useState<CartModel[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [totalPrice, setTotalPrice] = useState<number>(0);
  
    useEffect(() => {
      const calculateTotalPrice = () => {
        const total = cartItems.reduce((acc, product) => acc + product.product.price, 0);
        setTotalPrice(total);
      };
      calculateTotalPrice();
    }, [cartItems]);


  const fetchCartItems = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch('http://localhost:9091/api/cart', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('Failed to fetch cart items');
      }

      console.log('Cart items fetched successfully');

      const items: CartModel[] = await response.json();
      console.log(items);
      setCartItems(items);
    } catch (error: any) {
      console.error('Error fetching cart items:', error.message);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const removeProductFromCart = async (cartId: number) => {
    console.log(`Removing product from cart: ${cartId}`);
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:9091/api/cart/remove/${cartId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error(`Failed to remove product from cart: ${response.statusText}`);
      }

      console.log(`Removed from cart: ${cartId}`);
      setCartItems(cartItems.filter(item => item.product.productId !== cartId));
    } catch (error: any) {
      console.error('Error removing product from cart:', error.message);
      setError(error.message);
    } finally {
      setLoading(false);
      fetchCartItems();
    }
  };

  useEffect(() => {
    fetchCartItems();
  }, []);

  if (loading) {
    return <SpinnerLoading />;
  }

  if (error) {
    return <div>Error loading cart items: {error}</div>;
  }

  return (
    <div className="container">
      <div className="text-center" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '70px', padding: '80px' }}>
        <div style={{ backgroundColor: 'pink', borderRadius: '20px', padding: '10px', width: 'fit-content' }}>
          <h1>Вашата кошничка</h1>
        </div>
      </div>

      {cartItems.length > 0 ? (
        <div className="row justify-content-center" style={{ padding: '10px 10px 20px 10px' }}>
          {cartItems.map((product) => (
            <div className="col-md-4 mb-3" key={product.product.productId}>
              <div className="card">
                <div className="card-body">
                  <a href={product.product.imagePathURL} target="_blank" rel="noopener noreferrer">
                    <img src={`http://localhost:9091/api${product.product.imagePathURL}`} alt={product.product.name} className="rounded-img" style={{ width: '100%', height: 'auto', marginBottom: '10px' }} />
                  </a>
                  <h3 style={{ padding: '15px 0px 0px 0px' }}>{product.product.price} MKD / {product.product.price * 0.016} EUR</h3>
                  <div className="d-flex justify-content-between align-items-center" style={{ padding: '15px 0px 0px 0px' }}>
                    <button onClick={() => { removeProductFromCart(product.id) }} className="btn btn-dark mr-2"><i className="fas fa-minus" style={{ color: 'white' }}></i> Отстрани од кошничка</button>
                    <a href={`/product/${product.product.productId}`} className="btn btn-pink" style={{ backgroundColor: 'pink', color: 'white' }}>Детали</a>
                    <a href={`/payment/${encodeURIComponent(JSON.stringify(product))}/${product.product.price}`} className="btn btn-pink" style={{ backgroundColor: 'pink', color: 'white' }}>Купи</a>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div>No items in your cart.</div>
      )}
      <div style={{ position: 'fixed', bottom: '20px', right: '20px', textAlign: 'right' }}>
        <a href={`/payment/buyAll/${totalPrice}`} className="btn btn-pink" 
              style={{
                padding: '10px 20px',
                backgroundColor: '#784040',
                color: '#fff',
                border: 'none',
                borderRadius: '10px',
                fontSize: '16px',
                cursor: 'pointer',
                boxSizing: 'border-box',
                marginRight: '10px' 
              }}>
                Buy all: {totalPrice} MKD / {totalPrice * 0.016} EUR
            </a>
        <NavLink to="/home" style={{ textDecoration: 'none', display: 'inline-block' }}>
        <button
            style={{
              padding: '10px 20px',
              backgroundColor: '#784040',
              color: '#fff',
              border: 'none',
              borderRadius: '10px',
              fontSize: '16px',
              cursor: 'pointer',
              boxSizing: 'border-box',
            }}
          >
            Back
          </button>
        </NavLink>
      </div>
    </div>
  );
};

export default Cart;

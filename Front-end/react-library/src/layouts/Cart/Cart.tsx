import React, { useState, useEffect } from 'react';
import { Product } from '../../Types/types';
import { SpinnerLoading } from '../Utils/SpinnerLoading';
import { NavLink } from "react-router-dom";

const Cart: React.FC = () => {
  const [cartItems, setCartItems] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

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

      const items: Product[] = await response.json();
      setCartItems(items);
    } catch (error: any) {
      console.error('Error fetching cart items:', error.message);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const removeProductFromCart = async (productId: number) => {
    console.log(`Removing product from cart: ${productId}`);
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:9091/api/cart/remove/${productId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error(`Failed to remove product from cart: ${response.statusText}`);
      }

      console.log(`Removed from cart: ${productId}`);
      setCartItems(cartItems.filter(item => item.productId !== productId));
    } catch (error: any) {
      console.error('Error removing product from cart:', error.message);
      setError(error.message);
    } finally {
      setLoading(false);
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
            <div className="col-md-4 mb-3" key={product.productId}>
              <div className="card">
                <div className="card-body">
                  <a href={product.imagePathURL} target="_blank" rel="noopener noreferrer">
                    <img src={product.imagePathURL} alt={product.name} className="rounded-img" style={{ width: '100%', height: 'auto', marginBottom: '10px' }} />
                  </a>
                  <h3 style={{ padding: '15px 0px 0px 0px' }}>{product.price} MKD / {product.price * 0.016} EUR</h3>
                  <div className="d-flex justify-content-between align-items-center" style={{ padding: '15px 0px 0px 0px' }}>
                    <button onClick={() => { removeProductFromCart(product.productId) }} className="btn btn-dark mr-2"><i className="fas fa-minus" style={{ color: 'white' }}></i> Отстрани од кошничка</button>
                    <a href={`/product/${product.productId}`} className="btn btn-pink" style={{ backgroundColor: 'pink', color: 'white' }}>Детали</a>
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

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { CartModel } from '../../Types/types';
import { useHistory } from 'react-router-dom';
import { NavLink } from "react-router-dom";

interface PaymentParams {
  cart: string;
  price: string;
}

const Payment: React.FC = () => {
  const history = useHistory();

  const { cart } = useParams<PaymentParams>();
  const { price } = useParams<PaymentParams>();
  const [cartObject, setCartObject] = useState<CartModel>();
  const [form, setForm] = useState({
    name: '',
    cardNumber: '',
    expiryDate: '',
    cvv: '',
    address: ''
  });

  const [paymentMethod, setPaymentMethod] = useState('CARD');

  useEffect(() => {
  const parseCartString = (cartString: string): CartModel => {
    try {
      return JSON.parse(decodeURIComponent(cartString)) as CartModel;
    } catch (error) {
      console.error('Error parsing cart string:', error);
      return {
        id: -1,
        product: {
          productId: -1,
          quantity: -1,
          name: '',
          description: '',
          price: -1,
          category: { categoryId: -1, categoryName: '' },
          imagePathURL: ''
        }
      };
    }
  };

  if (cart !== "buyAll") {
    setCartObject(parseCartString(cart));
  }
}, [cart]); 

  const handlePayment = async (cartId?: number) => {
    console.log('Submitting payment for cart ID:', cartId);
    if (cartId != undefined){
        try{
            const response = await fetch(`http://localhost:9091/api/cart/orderProduct/${cartId}`, {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({
                    paymentMethod: paymentMethod,
                    address: form.address
                })
            });

                if (response.ok) {
                    alert('Payment submitted foyr product ID:' + cartObject?.product.productId);
                    history.push('/cart');
                }
                else{
                    alert('Payment failed for product ID:' + cartObject?.product.productId);
                }
            }
            catch(error: any){
                console.error('Error submitting payment:', error.message);
            }
    }
    else{
        try{
            const response = await fetch(`http://localhost:9091/api/cart/buyAll`, {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({
                    paymentMethod: paymentMethod,
                    address: form.address
                })
            });

                if (response.ok) {
                    alert('Payment submitted');
                    history.push('/cart');
                }
                else{
                    alert('Payment failed');
                }
            }
            catch(error: any){
                console.error('Error submitting payment:', error.message);
            }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    handlePayment(cartObject?.id);
  };

  const handlePaymentMethodChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPaymentMethod(e.target.value);
  };

  return (
    <div className="container">
      <h2>Payment for Product ID: {cartObject?.product.name}</h2>
        <div className="form-group">
            <label>Payment Method</label>
            <select
            value={paymentMethod}
            onChange={handlePaymentMethodChange}
            className="form-control"
            >
            <option value="CARD">Card</option>
            <option value="CASH">Cash</option>
            </select>
        </div>
      {paymentMethod === 'CARD' && (
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Name on Card</label>
          <input
            type="text"
            name="name"
            value={form.name}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label>Card Number</label>
          <input
            type="text"
            name="cardNumber"
            value={form.cardNumber}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label>Expiry Date</label>
          <input
            type="text"
            name="expiryDate"
            value={form.expiryDate}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label>CVV</label>
          <input
            type="text"
            name="cvv"
            value={form.cvv}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label>Address</label>
          <input
            type="text"
            name="address"
            value={form.address}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <br />
        <h3>Total price: { price } MKD</h3>
        <br />
      </form>
    )}{paymentMethod === 'CASH' && (
        <div>
            <div className="form-group">
            <label>Address</label>
            <input
            type="text"
            name="address"
            value={form.address}
            onChange={handleChange}
            className="form-control"
            required
            />
        </div>
        <br />
        <h3>Total price: { price } MKD</h3>
        <br />
    </div>
    )}
    <button
        onClick={() => {handlePayment(cartObject?.id)}}
        type="submit" className="btn btn-primary mt-3">Submit Payment
    </button>
    <div style={{ position: 'fixed', bottom: '20px', right: '20px', textAlign: 'right' }}>
        <NavLink to="/cart" style={{ textDecoration: 'none', display: 'inline-block' }}>
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

export default Payment;
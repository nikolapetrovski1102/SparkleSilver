import React, { useState, useEffect } from 'react';
import { useHistory, NavLink } from 'react-router-dom';
import Cookies from 'js-cookie';

const Login: React.FC = () => {
  const [usernameOrEmail, setUsernameOrEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const history = useHistory();

  useEffect(() => {
    // Check if the user is already logged in
    if (Cookies.get('userId')) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLogin = async () => {
    try {
      const response = await fetch('http://localhost:9091/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          usernameOrEmail,
          password,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        const userId = data.userId; // Assuming the response JSON contains userId
  
        // Set the cookie with the user ID
        document.cookie = `userId=${userId}; path=/`;

        history.push('/home');
      } else if (response.status === 404) {
        setError('User not found. Please check your username or email.');
      } else if (response.status === 400) {
        setError('Invalid username/email or password. Please try again.');
      } else {
        setError('An error occurred while logging in. Please try again later.');
      }
    } catch (error) {
      console.error('Error logging in:', error);
      setError('An error occurred while logging in. Please try again later.');
    }
  };

  if (isLoggedIn) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <div style={{ width: '90%', maxWidth: '470px', padding: '20px', textAlign: 'center' }}>
          <h2 style={{ marginBottom: '30px' }}>You are already logged in</h2>
          <button
            onClick={() => history.push('/home')}
            style={{
              padding: '8px 16px',
              backgroundColor: '#784040',
              color: '#fff',
              border: 'none',
              borderRadius: '10px',
              fontSize: '16px',
              cursor: 'pointer',
              width: '100%',
            }}
          >
            Go to Home
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div style={{ width: '90%', maxWidth: '470px', padding: '20px' }}>
        <h2 style={{ textAlign: 'center', marginTop: '20px', marginBottom: '30px' }}>Login</h2>
        {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="text"
            placeholder="Username or Email"
            value={usernameOrEmail}
            onChange={(e) => setUsernameOrEmail(e.target.value)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
          />
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <button
            onClick={handleLogin}
            style={{
              padding: '8px 16px',
              backgroundColor: '#784040',
              color: '#fff',
              border: 'none',
              borderRadius: '10px',
              fontSize: '16px',
              cursor: 'pointer',
              width: '100%',
            }}
          >
            Login
          </button>
        </div>
        <p style={{ textAlign: 'center', marginTop: '20px', fontSize: '14px' }}>
          Don't have an account? <NavLink to="/create-account" style={{ textDecoration: 'none', color: '#784040' }}>Create Account</NavLink>
        </p>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <div style={{ width: '50%' }}>
            <NavLink to="/home" style={{ textDecoration: 'none', width: '100%' }}>

            </NavLink>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

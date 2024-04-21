import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';

interface Props {
  onLogin: (credentials: { username: string, password: string }) => void;
}

const Login: React.FC<Props> = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    if (username.trim() === '' || password.trim() === '') {
      alert('Please enter username and password.');
      return;
    }
    onLogin({ username, password });
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div style={{ width: '90%', maxWidth: '470px', padding: '20px' }}>
        <h2 style={{ textAlign: 'center', marginTop: '20px', marginBottom: '30px' }}>Login</h2>
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
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
              padding: '8px 16px', // Decreased padding
              backgroundColor: '#784040',
              color: '#fff',
              border: 'none',
              borderRadius: '10px',
              fontSize: '16px',
              cursor: 'pointer',
              width: '100%'
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
              <button
                style={{
                  padding: '6px 8px', // Decreased padding
                  backgroundColor: '#784040',
                  color: '#fff',
                  border: 'none',
                  borderRadius: '10px',
                  fontSize: '14px', // Decreased font size
                  cursor: 'pointer',
                  width: '100%',
                  boxSizing: 'border-box',
                }}
              >
                Back
              </button>
            </NavLink>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;

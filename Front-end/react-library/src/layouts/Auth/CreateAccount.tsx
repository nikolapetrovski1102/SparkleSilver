import React, { useState } from 'react';
import { NavLink, useHistory } from 'react-router-dom';

const CreateAccount: React.FC = () => {
  const [username, setUsername] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [message, setMessage] = useState('');

  const history = useHistory();

  const handleRegister = async () => {
    if (password !== repeatPassword) {
      setMessage("Passwords do not match!");
      return;
    }

    if (!imageFile) {
      setMessage("Please upload an image.");
      return;
    }

    const uploadedFilePath = await uploadImage();
    if (uploadedFilePath) {
      const newUser = {
        username,
        firstName,
        lastName,
        email,
        password,
        repeatPassword,
        imagePathUrl: uploadedFilePath,
        userRoles: 1,  
        isActive: true  
      };

      try {
        const response = await fetch('http://localhost:9091/api/users/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          credentials: 'include',  
          body: JSON.stringify(newUser)
        });

        if (response.ok) {
          setMessage('Registration successful! Please check your email to verify your account.');
          history.push('/login')
        } else {
          const errorData = await response.json();
          setMessage(`Registration failed: ${errorData.message}`);
        }
      } catch (error) {
        console.error('Error during registration:', error);
        setMessage('Registration failed. Please try again.');
      }
    }
  };

  const uploadImage = async () => {
    if (!imageFile) return;

    const formData = new FormData();
    formData.append('image', imageFile);

    try {
      const response = await fetch('http://localhost:9091/api/upload', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        throw new Error('Failed to upload image');
      }

      const filePath = await response.text(); 
      return filePath;
    } catch (error) {
      console.error('Error uploading image:', error);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div style={{ width: '90%', maxWidth: '470px', padding: '20px' }}>
        <h2 style={{ textAlign: 'center', marginTop: '20px', marginBottom: '30px' }}>Create Account</h2>
        {message && <p style={{ color: 'red', textAlign: 'center' }}>{message}</p>}
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
            type="text"
            placeholder="First Name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
          />
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="text"
            placeholder="Last Name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
          />
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="password"
            placeholder="Repeat Password"
            value={repeatPassword}
            onChange={(e) => setRepeatPassword(e.target.value)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
          />
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', marginBottom: '20px' }}>
          <input
            type="file"
            name="image"
            id="image"
            onChange={(e) => setImageFile(e.target.files ? e.target.files[0] : null)}
            style={{ padding: '10px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
            accept="image/*"
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <button
            onClick={handleRegister}
            style={{
              padding: '8px 16px',
              backgroundColor: '#784040',
              color: '#fff',
              border: 'none',
              borderRadius: '10px',
              fontSize: '16px',
              cursor: 'pointer',
              width: '100%'
            }}
          >
            Create Account
          </button>
        </div>
        <p style={{ textAlign: 'center', marginTop: '20px', fontSize: '14px' }}>
          Already have an account? <NavLink to="/login" style={{ textDecoration: 'none', color: '#784040' }}>Login here</NavLink>
        </p>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
          <div style={{ width: '50%' }}>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateAccount;

import React, { useState, useEffect } from 'react';
import Navbar from '../NavbarAndFooter/Navbar';
import { NavLink } from "react-router-dom";
import Cookies from 'js-cookie';

export const HomePage = () => {
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    // Fetch API to retrieve user role based on session cookie
    const fetchUserRole = async () => {
      try {
        // Retrieve session cookie value
        const sessionValue = Cookies.get('session');

        // Check if session cookie exists
        if (sessionValue) {
          const response = await fetch(`http://localhost:9091/api/users/getUserRole/${sessionValue}`, {
            method: 'GET',
            credentials: 'include', // Include cookies in the request
          });

          if (response.ok) {
            const data = await response.json();
            setUserRole(data);
          } else {
            console.error('Failed to fetch user role');
          }
        } else {
          console.error('Session cookie not found');
        }
      } catch (error) {
        console.error('Error fetching user role:', error);
      }
    };

    fetchUserRole();
  }, []);

  return (
    <div>
      <Navbar />
      <main className="main-content">
        <h1 className='homepage-collection' style={{ color: 'white' }}>Collection 2024</h1>

              <div className="image-container">
                  <div className="image-wrapper">
                      <img src="https://media.tiffany.com/is/image/Tiffany/EcomBrowseM/-tiffany-hardwearsmall-wrap-necklace-38172794_1029509_ED.jpg?defaultImage=NoImageAvailableInternal" alt=" " />
                  </div>
                  <div className="image-wrapper">
                      <img src="https://www.uneakboutique.co.uk/cdn/shop/products/Sterling-Silver-Open-Sqiggle-Flower-Earrings-By-Elements-Silver_e895d93e-7c41-4029-946b-8fa5fc060dbd.jpg?v=1479321205" alt=" " />
                  </div>
                  <div className="image-wrapper">
                      <img src="https://www.lumije.com/cdn/shop/products/signature-14k-white-gold-four-prong-diamond-tennis-bracelet-h-colorsi-clarity-lumije-new-york-1.jpg?v=1654797342" alt=" " />
                   </div>
              </div>

            
              {userRole.toString() == '1' && (
                <div className='add-product-container'>
                  <div className='add-product'>
                    <NavLink to="/add-product" className="add-product-link">+</NavLink>
                    <h3>Додадете производ</h3>
                  </div>
                </div>
              )}

      </main>
    </div>
  );
};

export default HomePage;

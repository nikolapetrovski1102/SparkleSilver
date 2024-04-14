import React from 'react';
import Navbar from '../NavbarAndFooter/Navbar';
import { NavLink } from "react-router-dom";

export const HomePage = () => {
  return (
    <div>
      <Navbar />
      <main className="main-content">
        <h1 className='homepage-collection' style={{ color: 'white' }}>Collection 2024</h1>

              <div className="image-container">
                  <div className="image-wrapper">
                      <img src="https://media.tiffany.com/is/image/Tiffany/EcomBrowseM/-tiffany-hardwearsmall-wrap-necklace-38172794_1029509_ED.jpg?defaultImage=NoImageAvailableInternal" alt=" " />
                      <NavLink to="/add-product" className="add-icon">➕</NavLink>
                  </div>
                  <div className="image-wrapper">
                      <img src="https://www.uneakboutique.co.uk/cdn/shop/products/Sterling-Silver-Open-Sqiggle-Flower-Earrings-By-Elements-Silver_e895d93e-7c41-4029-946b-8fa5fc060dbd.jpg?v=1479321205" alt=" " />
                      <NavLink to="/add-product" className="add-icon">➕</NavLink>
                  </div>
                  <div className="image-wrapper">
                      <img src="https://www.lumije.com/cdn/shop/products/signature-14k-white-gold-four-prong-diamond-tennis-bracelet-h-colorsi-clarity-lumije-new-york-1.jpg?v=1654797342" alt=" " />
                      <NavLink to="/add-product" className="add-icon">➕</NavLink>
                   </div>
              </div>

              <div className='add-product-container'>
                  <div className='add-product'>
                      <NavLink to="/add-product" className="add-product-link">+</NavLink>
                      <h3>Додадете производ</h3>
                  </div>
    </div>

      </main>
    </div>
  );
};

export default HomePage;

import React, { useState, useEffect } from 'react';
import { Product, Category } from '../../Types/types';
import { SpinnerLoading } from '../Utils/SpinnerLoading';
import { NavLink } from "react-router-dom";

const ProductList: React.FC<{ category: string }> = ({ category }) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [allProducts, setAllProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const addProductToCart = async (product: Product) => {
    console.log(`Adding product to cart: ${product.productId}`);
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:9091/api/cart/addToCart/${product.productId}?qty=1`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productId: product.productId }),
        credentials: 'include'
      });
  
      if (!response.ok) {
        throw new Error(`Failed to add product to cart: ${response.statusText}`);
      }
  
      console.log(`Added to cart: ${product.name}`);
    } catch (error: any) {
      console.error('Error adding product to cart:', error.message);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    const fetchCategories = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch('http://localhost:9091/api/category');
        if (!response.ok) {
          throw new Error('Failed to fetch categories');
        }
        const categories: Category[] = await response.json();
        const categoryData = categories.find(cat => cat.categoryName.toLowerCase() === category);
        if (!categoryData) {
          throw new Error(`Category ${category} not found`);
        }
        // Simulate fetching products by category by filtering locally
        const filteredProducts = allProducts.filter(prod => prod.category.categoryName.toLowerCase() === category);
        setProducts(filteredProducts);
      } catch (error: any) {
        console.error('Error fetching categories:', error.message);
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    if (allProducts.length === 0) {
      fetch('http://localhost:9091/api/products') // Make sure you have this endpoint
        .then(res => res.json())
        .then(data => setAllProducts(data))
        .catch(err => console.log('Error fetching all products:', err));
    } else {
      fetchCategories();
    }
  }, [category, allProducts.length]);

  if (error) {
    return <div>Error loading products: {error}</div>;
  }

  function translateCategory(category: string): string {

    // Map specific categories to their translations
    switch (category) {
      case "earrings":
        return "ОБЕТКИ";
      case "bracelets":
        return "АЛКИ";
      case "rings":
        return "ПРСТЕНИ";
      case "necklaces":
        return "ОГРЛИЦИ";
      default:
        return category;
    }
  }


  return (
    <div className="container">

      <div className="text-center" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '70px', padding: '80px' }}>
        <div style={{ backgroundColor: 'pink', borderRadius: '20px', padding: '10px', width: 'fit-content' }}>
          <h1>{translateCategory(category)}</h1>
        </div>
      </div>

      {loading ? (
        <SpinnerLoading />
      ) : (
        <div className="row justify-content-center" style={{ padding: '10px 10px 20px 10px' }}>
          {products.length > 0 ? (
            products.map((product) => (
              <div className="col-md-4 mb-3" key={product.productId}>
                <div className="card">
                  <div className="card-body" style={{ display: 'flex', justifyContent: 'center' }}>
                    <img src={`http://localhost:9091/api${product.imagePathURL}`} alt={product.name} className="rounded-img" style={{ width: '100px', height: '130px', marginBottom: '10px' }} />
                  </div>
                </div>
                <h3 style={{ padding: '15px 0px 0px 0px' }}>{product.price} MKD / {product.price * 0.016} EUR</h3>

                <div className="d-flex justify-content-between align-items-center" style={{ padding: '15px 0px 0px 0px' }}>
                  <button onClick={() => { addProductToCart(product) }} className="btn btn-dark mr-2"><i className="fas fa-plus" style={{ color: 'white' }}></i> Додади во кошничка </button>
                  <a href={`/product/${product.productId}`} className="btn btn-pink" style={{ backgroundColor: 'pink', color: 'white' }}>Детали</a>
                </div>
              </div>
            ))
          ) : (
            <div>No products found in this category.</div>
          )}
        </div>


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

export default ProductList;
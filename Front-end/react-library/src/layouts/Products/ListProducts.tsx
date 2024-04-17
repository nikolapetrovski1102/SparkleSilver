import React, { useState, useEffect } from 'react';
import { Product } from '../../Types/types';
import { SpinnerLoading } from '../Utils/SpinnerLoading';

const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch('http://localhost:9091/api/products');
        if (!response.ok) {
          throw new Error('Failed to fetch products');
        }
        const data = await response.json();
        setProducts(data);
      } catch (error) {
        console.error('Error fetching products:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  return (
    <div className="container">
      <h1 className="text-center">Product List</h1>
      {loading ? (
        <SpinnerLoading /> 
      ) : (
        <div className="row justify-content-center">
          {products.map((product) => (
            <div className="col-md-4 mb-3" key={product.productId}>
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{product.name}</h5>
                  <p className="card-text">{product.description}</p>
                  <p className="card-text">Price: ${product.price}</p>
                  <p className="card-text">Quantity: {product.quantity}</p>
                  <p className="card-text">Category: {product.category.categoryName}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ProductList;

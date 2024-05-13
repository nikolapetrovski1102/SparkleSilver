import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ProductModel from '../../models/product';

interface RouteParams {
  productId: string; // Define the type of productId
}

const ProductDetails: React.FC = () => {
  const { productId } = useParams<RouteParams>(); // Specify the type of useParams
  const [product, setProduct] = useState<ProductModel | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProductDetails = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch(`http://localhost:9091/api/products/${productId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch product details');
        }
        const productData: ProductModel = await response.json();
        setProduct(productData);
      } catch (error) {
        console.error('Error fetching product details:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProductDetails();
  }, [productId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!product) {
    return <div>Product not found</div>;
  }

  return (
    <div className="container">
      <h1>Product Details</h1>
      <div>
        <h2>{product.name}</h2>
        <p>Description: {product.description}</p>
        <p>Price: {product.price}</p>
        <p>Quantity: {product.quantity}</p>
        <img src={product.imageUrl} alt={product.name} style={{ width: '100%', maxWidth: '300px' }} />
        {/* Add more product details here as needed */}
      </div>
    </div>
  );
};

export default ProductDetails;

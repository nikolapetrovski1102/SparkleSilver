import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import ProductModel from '../../models/product';

interface RouteParams {
  productId: string;
}

const Container = styled.div`
  max-width: 800px;
  margin: 20px auto;
  padding: 30px;
  background-color: #ffe6e6;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: 'Poppins', sans-serif;
  text-align: center;
`;

const ProductTitle = styled.h1`
  color: #784040;
  font-size: 3em;
  margin-bottom: 30px;
`;

const ProductInfo = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
`;

const ProductImage = styled.img`
  width: 300px;
  height: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

const DetailsContainer = styled.div`
  margin-left: 20px;
  text-align: left;
`;

const ProductDescription = styled.p`
  color: #333;
  font-size: 1.2em;
  margin-bottom: 25px;
  line-height: 1.6;
`;

const ProductDetail = styled.div`
  color: #333;
  font-size: 1.2em;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
`;

const Label = styled.span`
  font-weight: bold;
  margin-right: 10px;
`;

const Value = styled.span`
  font-size: 1.1em;
  background-color: #d3b3b3;
  padding: 6px 12px;
  border-radius: 5px;
`;

const ProductDetails: React.FC = () => {
  const { productId } = useParams<RouteParams>();
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
        setError('Failed to fetch product details. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchProductDetails();
  }, [productId]);

  if (loading) {
    return <Container>Loading...</Container>;
  }

  if (error) {
    return <Container>Error: {error}</Container>;
  }

  if (!product) {
    return <Container>Product not found</Container>;
  }

  return (
    <Container>
      <ProductTitle>Product Details</ProductTitle>
      <ProductInfo>
        <ProductImage src={product.imageUrl} alt={product.name} />
        <DetailsContainer>
          <ProductTitle>{product.name}</ProductTitle>
          <Label>Description:</Label>
          <ProductDescription>{product.description}</ProductDescription>
          <ProductDetail>
            <Label>Price:</Label>
            <Value>${product.price}</Value>
          </ProductDetail>
          <ProductDetail>
            <Label>Quantity:</Label>
            <Value>{product.quantity}</Value>
          </ProductDetail>
        </DetailsContainer>
      </ProductInfo>
    </Container>
  );
};

export default ProductDetails;

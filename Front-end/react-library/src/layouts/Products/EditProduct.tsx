import React, { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import ProductModel from '../../models/product';
import { Category } from '../../Types/types';
import CategoryModel from '../../models/category';

interface RouteParams {
  productId: string;
}

const Container = styled.div`
  max-width: 600px;
  margin: 20px auto;
  padding: 30px;
  background-color: #ffe6e6;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: 'Arial, sans-serif';
  @media (max-width: 768px) {
    padding: 20px;
    margin: 10px;
  }
`;

const Title = styled.h1`
  text-align: center;
  color: #333;
  margin-bottom: 30px;
  font-size: 2em;
  @media (max-width: 768px) {
    font-size: 1.5em;
    margin-bottom: 20px;
  }
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
`;

const Label = styled.label`
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
  display: block;
`;

const Input = styled.input`
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  transition: border-color 0.3s;

  &:focus {
    border-color: #007bff;
    outline: none;
  }
`;

const Textarea = styled.textarea`
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  height: 120px;
  resize: vertical;
  transition: border-color 0.3s;

  &:focus {
    border-color: #007bff;
    outline: none;
  }
`;

const Select = styled.select`
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  transition: border-color 0.3s;

  &:focus {
    border-color: #007bff;
    outline: none;
  }
`;

const Button = styled.button`
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  background-color: #784040;
  color: #fff;
  cursor: pointer;
  font-size: 1em;
  transition: background-color 0.3s, transform 0.3s;

  &:hover {
    background-color: #9f5656;
    transform: translateY(-2px);
  }

  &:active {
    background-color: #004494;
  }
`;

const SaveButton = styled(Button)`
  background-color: #784040;

  &:hover {
    background-color: #9f5656;
  }
`;

const DeleteButton = styled(Button)`
  background-color: #a94442;
  margin-top: 16px; // Adds spacing between save and delete buttons

  &:hover {
    background-color: #d9534f;
  }
`;


const EditProduct: React.FC = () => {
  const history = useHistory();
  const { productId } = useParams<RouteParams>();
  const [product, setProduct] = useState<ProductModel | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState<ProductModel | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);

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
        setFormData(productData);
      } catch (error) {
        console.error('Error fetching product details:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProductDetails();
  }, [productId]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch('http://localhost:9091/api/category');
        if (!response.ok) {
          throw new Error('Failed to fetch categories');
        }
        const data: Category[] = await response.json();
        setCategories(data);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    fetchCategories();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    if (formData) {
      setFormData((prevState: ProductModel | null) => ({
        ...prevState!,
        [name]: value,
      }));
    }
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const categoryId = e.target.value;
    if (formData && categoryId) {
      const selectedCategory: CategoryModel | undefined = categories.find(category => category.categoryId === parseInt(categoryId));
      setFormData((prevState: ProductModel | null) => ({
        ...prevState!,
        category: selectedCategory || null,
      }));
    }
  };
  const handleDelete = async () => {
  if (!window.confirm("Are you sure you want to delete this product?")) return;

  try {
    const response = await fetch(`http://localhost:9091/api/products/delete/${productId}`, {
      method: 'DELETE'
    });
    if (!response.ok) {
      throw new Error('Failed to delete product');
    }
    history.push('/home'); // Redirect user after deletion
  } catch (error) {
    console.error('Error deleting product:', error);
  }
};


  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!formData) return;
  
    const categoryId = formData.category ? formData.category.categoryId : null;
  
    try {
      const response = await fetch(`http://localhost:9091/api/products/edit/${productId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          ...formData,
          category: categoryId,
        }),
      });
      if (!response.ok) {
        throw new Error('Failed to edit product');
      }
      history.push(`/product/${productId}`);
    } catch (error) {
      console.error('Error editing product:', error);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!product || !formData) {
    return <div>Product not found</div>;
  }

  return (
    <Container>
      <Title>Edit Product</Title>
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label>Name:</Label>
          <Input type="text" name="name" value={formData.name} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label>Description:</Label>
          <Textarea name="description" value={formData.description} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label>Price:</Label>
          <Input type="number" name="price" value={formData.price} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label>Quantity:</Label>
          <Input type="number" name="quantity" value={formData.quantity} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label>Image URL:</Label>
          <Input type="text" name="imageUrl" value={formData.imageUrl} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label>Category:</Label>
          <Select name="category" value={formData.category ? formData.category.categoryId.toString() : ''} onChange={handleCategoryChange}>
            <option value="">Select Category</option>
            {categories.map(category => (
              <option key={category.categoryId} value={category.categoryId.toString()}>
                {category.categoryName}
              </option>
            ))}
          </Select>
        </FormGroup>
        <SaveButton type="submit">Save Changes</SaveButton>
        <DeleteButton type="button" onClick={handleDelete}>Delete Product</DeleteButton>
      </Form>
    </Container>
  );
};

export default EditProduct;

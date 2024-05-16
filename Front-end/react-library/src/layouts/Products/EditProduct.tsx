import React, { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import ProductModel from '../../models/product';
import { Category } from '../../Types/types';
import CategoryModel from '../../models/category';

interface RouteParams {
  productId: string; // Define the type of productId
}

const EditProduct: React.FC = () => {
  const history = useHistory();
  const { productId } = useParams<RouteParams>(); // Specify the type of useParams
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
      // Find the corresponding category object based on categoryId
      const selectedCategory: CategoryModel | undefined = categories.find(category => category.categoryId === parseInt(categoryId));
      setFormData((prevState: ProductModel | null) => ({
        ...prevState!,
        category: selectedCategory || null, // Set selectedCategory or null if not found
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!formData) return;
  
    // Extract category ID from formData
    const categoryId = formData.category ? formData.category.categoryId : null;
  
    try {
      const response = await fetch(`http://localhost:9091/api/products/edit/${productId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          ...formData,
          category: categoryId, // Include category ID in the request body
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
    <div className="container">
      <h1>Edit Product</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} />
        </div>
        <div>
          <label>Description:</label>
          <textarea name="description" value={formData.description} onChange={handleChange} />
        </div>
        <div>
          <label>Price:</label>
          <input type="number" name="price" value={formData.price} onChange={handleChange} />
        </div>
        <div>
          <label>Quantity:</label>
          <input type="number" name="quantity" value={formData.quantity} onChange={handleChange} />
        </div>
        <div>
          <label>Image URL:</label>
          <input type="text" name="imageUrl" value={formData.imageUrl} onChange={handleChange} />
        </div>
        <div>
          <label>Category:</label>
          <select name="category" value={formData.category ? formData.category.categoryId.toString() : ''} onChange={handleCategoryChange}>
            <option value="">Select Category</option>
            {categories.map(category => (
              <option key={category.categoryId} value={category.categoryId.toString()}>
                {category.categoryName}
              </option>
            ))}
          </select>
        </div>
        <button type="submit">Save Changes</button>
      </form>
    </div>
  );
  
};

export default EditProduct;

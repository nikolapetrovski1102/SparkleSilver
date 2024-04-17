import React, { useState, useEffect } from 'react';
import { Category } from '../../Types/types';

const AddProductForm: React.FC = () => {
  const [productData, setProductData] = useState({
    quantity: '',
    name: '',
    description: '',
    price: '',
    category: '',
  });
  const [categories, setCategories] = useState<Category[]>([]);

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
    setProductData({
      ...productData,
      [name]: value,
    });
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const categoryId = e.target.value;
    setProductData({
      ...productData,
      category: categoryId,
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:9091/api/products/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData),
      });
      if (!response.ok) {
        throw new Error('Failed to add product');
      }
      // Reset form data after successful submission
      setProductData({
        quantity: '',
        name: '',
        description: '',
        price: '',
        category: '',
      });
      // You can add further handling here, like displaying a success message
    } catch (error) {
      console.error('Error adding product:', error);
    }
  };

  return (
    <div>
      <h2>Add Product</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            Quantity:
            <input type="number" name="quantity" value={productData.quantity} onChange={handleChange} />
          </label>
        </div>
        <div>
          <label>
            Name:
            <input type="text" name="name" value={productData.name} onChange={handleChange} />
          </label>
        </div>
        <div>
          <label>
            Description:
            <textarea name="description" value={productData.description} onChange={handleChange} />
          </label>
        </div>
        <div>
          <label>
            Price:
            <input type="number" step="0.01" name="price" value={productData.price} onChange={handleChange} />
          </label>
        </div>
        <div>
          <label>
            Category:
            <select name="category" value={productData.category} onChange={handleCategoryChange}>
              <option value="">Select Category</option>
              {categories.map((category) => (
                <option key={category.categoryId} value={category.categoryId}>
                  {category.categoryName}
                </option>
              ))}
            </select>
          </label>
        </div>
        <button type="submit">Add Product</button>
      </form>
    </div>
  );
};

export default AddProductForm;

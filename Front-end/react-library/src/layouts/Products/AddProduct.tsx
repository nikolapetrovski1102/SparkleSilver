import React, { useState, useEffect } from 'react';
import { Category } from '../../Types/types';
import { NavLink } from "react-router-dom";

interface ProductData {
  quantity: string;
  name: string;
  description: string;
  price: string;
  category: string;
  url: string; // Include url field for the image name
}

const AddProductForm: React.FC = () => {
  const [productData, setProductData] = useState<ProductData>({
    quantity: '',
    name: '',
    description: '',
    price: '',
    category: '',
    url: '',
  });
  const [imageFile, setImageFile] = useState<File | null>(null); // Separate state for the image file
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

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files ? e.target.files[0] : null;
    if (file) {
      setImageFile(file);
      setProductData({
        ...productData,
        url: file.name, // Set the file name in the url field
      });
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

      const filePath = await response.text(); // The server returns the file path
      return filePath;
    } catch (error) {
      console.error('Error uploading image:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // Upload the image first
    const uploadedFilePath = await uploadImage();
    if (uploadedFilePath) {
      // Prepare data to send to the backend
      const dataToSend = {
        quantity: productData.quantity, // Ensure quantity is set
        name: productData.name, // Ensure name is set
        description: productData.description, // Ensure description is set
        price: productData.price, // Ensure price is set
        category: productData.category, // Ensure category is set
        imagePathURL: uploadedFilePath, // Ensure imagePathURL is set with the uploaded file path
      };

      try {
        const response = await fetch('http://localhost:9091/api/products/add', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(dataToSend),
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
          url: '',
        });
        setImageFile(null); // Reset image file state

        // You can add further handling here, like displaying a success message
      } catch (error) {
        console.error('Error adding product:', error);
      }
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <div style={{ width: '90%', maxWidth: '470px', padding: '20px'}}>
        <h2 style={{ textAlign: 'center', marginTop: '10px', marginBottom: '30px' }}>Add Product</h2>
        <form onSubmit={handleSubmit} style={{ display: 'grid', gridGap: '20px' }}>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <input type="text" name="name" id="name" value={productData.name} onChange={handleChange} style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }} placeholder="Name" />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <textarea name="description" id="description" value={productData.description} onChange={handleChange} style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6', minHeight: '100px' }} placeholder="Description" />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <input type="number" name="quantity" id="quantity" value={productData.quantity} onChange={handleChange} style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }} placeholder="Quantity" />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <input type="number" step="0.01" name="price" id="price" value={productData.price} onChange={handleChange} style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px',backgroundColor: '#ffe6e6' }} placeholder="Price" />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <input
              type="file"
              name="image"
              id="image"
              onChange={handleImageChange}
              style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}
              accept="image/*"
            />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <select name="category" id="category" value={productData.category} onChange={handleCategoryChange} style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '10px', fontSize: '16px', backgroundColor: '#ffe6e6' }}>
              <option value="">Select Category</option>
              {categories.map((category) => (
                <option key={category.categoryId} value={category.categoryId}>
                  {category.categoryName}
                </option>
              ))}
            </select>
          </div>
          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <button
              type="submit"
              style={{
                padding: '10px 20px',
                backgroundColor: '#784040',
                color: '#fff',
                border: 'none',
                borderRadius: '10px',
                fontSize: '16px',
                cursor: 'pointer',
                width: '48%',
              }}
            >
              Add Product
            </button>
            <NavLink to="/home" style={{ textDecoration: 'none', width: '48%' }}>
              <button
                style={{
                  padding: '10px 0',
                  backgroundColor: '#784040',
                  color: '#fff',
                  border: 'none',
                  borderRadius: '10px',
                  fontSize: '16px',
                  cursor: 'pointer',
                  width: '100%',
                  boxSizing: 'border-box',
                }}
              >
                Back
              </button>
            </NavLink>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AddProductForm;

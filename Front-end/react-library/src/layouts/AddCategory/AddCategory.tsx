import React, { useState } from 'react';


const AddCategoryForm: React.FC = () => {
  const [categoryName, setCategoryName] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:9091/api/category/add?categoryName=${encodeURIComponent(categoryName)}`, {
        method: 'POST',
      });
      if (!response.ok) {
        throw new Error('Failed to add category');
      }
      console.log('Category added successfully');
    } catch (error) {
      console.error('Error adding category:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCategoryName(e.target.value);
  };

  return (
    <div>
      <h2>Add Category</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Category Name:
          <input type="text" value={categoryName} onChange={handleChange} required />
        </label>
        <button type="submit">Add Category</button>
      </form>
    </div>
  );
};

export default AddCategoryForm;

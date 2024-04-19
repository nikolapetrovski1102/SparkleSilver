export interface Product {
    productId: number;
    quantity: number;
    name: string;
    description: string;
    price: number;
    category: Category; 
  }

  export interface Category {
    categoryId: number;
    categoryName: string;
  }
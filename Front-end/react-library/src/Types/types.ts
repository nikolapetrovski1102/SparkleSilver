export interface Product {
    productId: number;
    quantity: number;
    name: string;
    description: string;
    price: number;
    category: Category; 
    imagePathURL: string; 
  }

  export interface Category {
    categoryId: number;
    categoryName: string;
  }

  export interface CartModel {
    id: number;
    product: Product;
  }
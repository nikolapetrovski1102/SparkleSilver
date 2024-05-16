import CategoryModel from "./category";

class ProductModel {
    productId: number;
    quantity: number;
    name?: string;
    description?: string;
    price?: number;
    imageUrl: string;
    category: CategoryModel | null; // CategoryModel or null if category is optional

    constructor(productId: number, quantity: number, name: string, description: string, price: number, imageUrl: string, category: CategoryModel | null) {
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}

export default ProductModel;

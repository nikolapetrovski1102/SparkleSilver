class ProductModel {

    productId: number;
    quantity: number;
    name?: string;
    description?: string;
    price?: number;
    imageUrl: string;

    constructor(productId: number, quantity: number, name: string, description: string , price: number, imageUrl: string) {

        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }


}

export default ProductModel;
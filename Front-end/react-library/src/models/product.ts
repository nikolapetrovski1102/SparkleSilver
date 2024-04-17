class ProductModel {

    productId: number;
    quantity: number;
    name?: string;
    description?: string;
    price?: number;

    constructor(productId: number, quantity: number, name: string, description: string , price: number) {

        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
    }


}

export default ProductModel;
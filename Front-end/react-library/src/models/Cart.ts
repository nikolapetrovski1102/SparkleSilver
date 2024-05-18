import ProductModel from "./product";


class CartModel {
    quantity: number;
    product: ProductModel;

    constructor(quantity: number, product: ProductModel) {
        this.quantity = quantity;
        this.product = product;
    }

}

export default ProductModel;

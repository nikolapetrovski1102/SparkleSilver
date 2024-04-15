package com.example.springsilver.service;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.OrderItem;

import java.util.List;

public interface CartService {

    List<OrderItem> listAllProductInCart(Long cartId);
    Cart addProductItemToShoppingCart(String username,Long productId, int quantity);
}

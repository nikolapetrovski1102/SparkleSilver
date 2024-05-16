package com.example.springsilver.service;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CartService {

    List<Product> listAllProductInCart(Long cartId);
    Cart addToCart(Long id, Integer qty, Long userId);
    void removeFromCart (Long cartId);
    Cart getProduct (Long cartId);
}

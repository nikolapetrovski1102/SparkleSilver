package com.example.springsilver.service.impl;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.Users;
import com.example.springsilver.models.exceptions.InvalidUserCredentialsException;
import com.example.springsilver.models.exceptions.ProductNotFoundException;
import com.example.springsilver.models.exceptions.ShoppingCartNotFoundException;
import com.example.springsilver.repository.OrderItemRepository;
import com.example.springsilver.repository.ProductRepository;
import com.example.springsilver.repository.ShoppingCartRepository;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderItem> listAllProductInCart(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent()) {
            throw new ShoppingCartNotFoundException(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getOrderItems();
    }

    @Override
    public Cart addProductItemToShoppingCart(String username, Long productId, int quantity) {
        if (username == null || username.isEmpty()) {
            throw new InvalidUserCredentialsException();
        }
        Users user = (Users) this.userRepository.findByUsername(username).get();
        Cart cart = this.shoppingCartRepository.findById(user.getId()).get();

        if (!productRepository.findById(productId).isPresent()) {
            throw new ProductNotFoundException(productId);
        }
        Product product = productRepository.findById(productId).get();
        OrderItem orderItem = orderItemRepository.save(new OrderItem(quantity, product.getPrice() * quantity, product,null));
        cart.getOrderItems().add(orderItem);
        return this.shoppingCartRepository.save(cart);

    }
}

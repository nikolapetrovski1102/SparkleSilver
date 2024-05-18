package com.example.springsilver.service.impl;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.Users;
import com.example.springsilver.models.exceptions.InvalidUserCredentialsException;
import com.example.springsilver.models.exceptions.ProductNotFoundException;
import com.example.springsilver.models.exceptions.ShoppingCartNotFoundException;
import com.example.springsilver.models.exceptions.UserIdNotFoundException;
import com.example.springsilver.repository.OrderItemRepository;
import com.example.springsilver.repository.ProductRepository;
import com.example.springsilver.repository.ShoppingCartRepository;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Product> listAllProductInCart(Long userId) {
        Users user = userRepository.findUsersById(userId).orElseThrow(UserIdNotFoundException::new);

        try{
            List<Cart> cart = shoppingCartRepository.findByUsers(user).stream().toList();
            List<Product> productList = new ArrayList<>();

            if (!cart.isEmpty()) {
                for (Cart item : cart) {
                    productList.add(item.getProduct());
                }
            }

            return productList;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }


    }

    @Override
    public void removeFromCart(Long cartId) {

        Cart cart = shoppingCartRepository.findCartById(cartId);

        shoppingCartRepository.delete(cart);
    }

    @Override
    public Cart addToCart(Long id, Integer qty, Long userId) {
        Product product = this.productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException(id));

        Users user = this.userRepository.findUsersById(userId).orElseThrow(UserIdNotFoundException::new);

        Cart cart = new Cart(qty, product, user);

        return shoppingCartRepository.save(cart);
    }

    @Override
    public Cart getProduct(Long cartId) {
        return shoppingCartRepository.findCartById(cartId);
    }
}

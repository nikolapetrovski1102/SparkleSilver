package com.example.springsilver;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.Users;
import com.example.springsilver.models.exceptions.ProductNotFoundException;
import com.example.springsilver.models.exceptions.UserIdNotFoundException;
import com.example.springsilver.repository.OrderItemRepository;
import com.example.springsilver.repository.ProductRepository;
import com.example.springsilver.repository.ShoppingCartRepository;
import com.example.springsilver.repository.UserRepository;
import com.example.springsilver.service.impl.CartServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void testListAllProductInCart_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findUsersById(userId)).thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () -> cartService.listAllProductInCart(userId));
    }

    @Test
    public void testListAllProductInCart_Success() {
        Long userId = 1L;
        Users user = new Users();
        when(userRepository.findUsersById(userId)).thenReturn(Optional.of(user));

        Product product1 = new Product();
        Product product2 = new Product();
        Cart cart1 = new Cart(1, product1, user);
        Cart cart2 = new Cart(2, product2, user);
        List<Cart> carts = Arrays.asList(cart1, cart2);

        when(shoppingCartRepository.findByUsers(user)).thenReturn(carts);

        List<Cart> result = cartService.listAllProductInCart(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(shoppingCartRepository).findByUsers(user);
    }

    @Test
    public void testRemoveFromCart() {
        Long cartId = 1L;
        Cart cart = new Cart();
        when(shoppingCartRepository.findCartById(cartId)).thenReturn(cart);

        cartService.removeFromCart(cartId);

        verify(shoppingCartRepository).delete(cart);
    }

    @Test
    public void testAddToCart_ProductNotFound() {
        Long productId = 1L;
        Long userId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.addToCart(productId, 1, userId));
    }

    @Test
    public void testAddToCart_UserNotFound() {
        Long productId = 1L;
        Long userId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(userRepository.findUsersById(userId)).thenReturn(Optional.empty());

        assertThrows(UserIdNotFoundException.class, () -> cartService.addToCart(productId, 1, userId));
    }

    @Test
    public void testAddToCart_Success() {
        Long productId = 1L;
        Long userId = 1L;
        Product product = new Product();
        Users user = new Users();
        Cart cart = new Cart(1, product, user);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(userRepository.findUsersById(userId)).thenReturn(Optional.of(user));
        when(shoppingCartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addToCart(productId, 1, userId);

        assertNotNull(result);
        assertEquals(product, result.getProduct());
        assertEquals(user, result.getUsers());
        verify(shoppingCartRepository).save(any(Cart.class));
    }

    @Test
    public void testGetProduct() {
        Long cartId = 1L;
        Cart cart = new Cart();
        when(shoppingCartRepository.findCartById(cartId)).thenReturn(cart);

        Cart result = cartService.getProduct(cartId);

        assertNotNull(result);
        assertEquals(cart, result);
        verify(shoppingCartRepository).findCartById(cartId);
    }
}


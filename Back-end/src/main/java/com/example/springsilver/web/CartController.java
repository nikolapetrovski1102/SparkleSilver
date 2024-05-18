package com.example.springsilver.web;

import com.example.springsilver.config.CookieManager;
import com.example.springsilver.models.*;
import com.example.springsilver.models.dto.OrderItemDto;
import com.example.springsilver.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
public class CartController {

    private final CartService cartService;
    private final CookieManager cookieManager;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;
    private final OrdersService ordersService;

    public CartController(CartService cartService, CookieManager cookieManager, OrderItemService orderItemService, PaymentService paymentService, ShippingService shippingService, OrdersService ordersService) {
        this.cartService = cartService;
        this.cookieManager = cookieManager;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
        this.shippingService = shippingService;
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> productsInCart (HttpServletRequest req){

        String userId = cookieManager.checkSession(req);

        if (!userId.equals("Not authorized")){
            return ResponseEntity.ok(cartService.listAllProductInCart(Long.parseLong(userId)));
        }
        else{
            return ResponseEntity.status(401).build();
        }

    }

    @PostMapping("/remove/{cartId}")
    public ResponseEntity<?> removeFromCart (@PathVariable Long cartId){

        try {
            cartService.removeFromCart(cartId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            System.out.println(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestParam Integer qty, HttpServletRequest req){
        String user = cookieManager.checkSession(req);
        if (!user.equals("Not authorized")) {
            try {
                this.cartService.addToCart(id, qty, Long.parseLong(user));
                return ResponseEntity.ok().build();
            }
            catch (Exception ex){
                System.out.println(ex.toString());
                return ResponseEntity.internalServerError().build();
            }

        }
        else{
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession (HttpServletRequest req){

         String session = cookieManager.checkSession(req);

         if (session.equals("Not authorized")){
             return ResponseEntity.badRequest().build();
         }
         else{
             return ResponseEntity.ok(session);
         }

    }

    @PostMapping("/orderProduct/{cartId}")
    public ResponseEntity<?> orderProduct (@PathVariable Long cartId, @RequestBody OrderItemDto itemDto){

        Cart cartProduct = cartService.getProduct(cartId);

        Payment payment = new Payment(itemDto.getPaymentMethod(), cartProduct.getProduct().getPrice());
        paymentService.save(payment);
        Shipping shipping = new Shipping(itemDto.getAddress());
        shippingService.save(shipping);
        Orders orders = new Orders(payment, cartProduct.getProduct().getPrice(), shipping);
        ordersService.save(orders);
        OrderItem orderItem = new OrderItem(cartProduct.getQuantity(), cartProduct.getProduct().getPrice(), cartProduct.getProduct(), orders);

        return ResponseEntity.ok(orderItemService.save(orderItem));

    }

}

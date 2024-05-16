package com.example.springsilver.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @OneToMany
    @JoinColumn(name = "order_item_id", nullable = true)
    private List<OrderItem> orderItems;

    public Cart(Integer quantity, Product product, Users users, List<OrderItem> orderItems) {
        this.quantity = quantity;
        this.product = product;
        this.users = users;
        this.orderItems = orderItems;
    }
}

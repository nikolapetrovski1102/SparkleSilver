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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


    @OneToMany
    @JoinColumn(name = "order_item_id")
    private List<OrderItem> orderItems;

}

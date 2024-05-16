package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.Order;

@Data
@Entity
@NoArgsConstructor
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long OrderItem_Id;

    private int quantity;
    private float price;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = true)
    private Orders order;

    public OrderItem(int quantity, float price, Long productId,Orders orders) {
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.order = orders;
    }

}

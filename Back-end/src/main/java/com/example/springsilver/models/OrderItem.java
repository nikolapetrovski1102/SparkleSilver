package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
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
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Orders order;

    public OrderItem(int quantity, float price, Product product, Orders orders) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = orders;
    }

}

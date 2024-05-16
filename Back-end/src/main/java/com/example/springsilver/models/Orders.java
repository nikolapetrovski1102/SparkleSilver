package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orders_id;

    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "total_price")
    private float total_price;

    @OneToMany
    private List<OrderItem> order_items;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    public void Total() {
        for (OrderItem oi : order_items) {
            total_price += oi.getPrice();
        }
    }

    public Orders(Payment payment, float total_price, Shipping shipping) {
        this.orderDate = LocalDate.now();
        this.payment = payment;
        this.total_price = total_price;
        this.shipping = shipping;
        this.order_items = new ArrayList<OrderItem>();
        Total();
    }


}

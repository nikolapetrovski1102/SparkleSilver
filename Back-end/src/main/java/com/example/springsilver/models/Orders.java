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
    @Column(name = "Orders_id")
    private Long orders_id;
    private LocalDate order_data;
    private Double total_price;

    @OneToMany
    private List<OrderItem> order_items;

    @ManyToOne
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

    public Orders(Payment payment, Shipping shipping) {
        this.order_data = LocalDate.now();
        this.payment = payment;
        this.shipping = shipping;
        this.order_items = new ArrayList<OrderItem>();
        Total();
    }


}

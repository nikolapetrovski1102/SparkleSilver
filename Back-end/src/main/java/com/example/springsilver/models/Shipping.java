package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Shipping")
public class Shipping {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long shipping_id;
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;
    private String address;

    public Shipping(String address) {
        this.shipmentDate = LocalDate.now().plusWeeks(1);
        this.address = address;
    }
}

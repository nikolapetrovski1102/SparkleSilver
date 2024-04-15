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
    private LocalDate shipment_date;
    private String address;

    public Shipping(LocalDate shipment_date, String address) {
        this.shipment_date = shipment_date; //dali da ima dva data ? ispr : primanje
        this.address = address;
    }
}

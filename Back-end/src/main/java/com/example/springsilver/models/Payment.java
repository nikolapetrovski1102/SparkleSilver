package com.example.springsilver.models;


import com.example.springsilver.models.enumeration.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long payment_id;
    private LocalDate payment_date;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double amount;

    public Payment(LocalDate payment_date, PaymentMethod paymentMethod, Double amount) {

        this.payment_date = payment_date;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}

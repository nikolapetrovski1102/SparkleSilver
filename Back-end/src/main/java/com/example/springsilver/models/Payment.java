package com.example.springsilver.models;


import com.example.springsilver.models.enumeration.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long payment_id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    private float amount;

    public Payment(PaymentMethod paymentMethod, float amount) {
        this.paymentDate = LocalDate.now();
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}

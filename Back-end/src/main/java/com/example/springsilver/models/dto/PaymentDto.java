package com.example.springsilver.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {
    private LocalDate paymentDate;
    private String paymentMethod;
    private Double amount;

    public PaymentDto(LocalDate paymentDate, String paymentMethod, Double amount) {
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}

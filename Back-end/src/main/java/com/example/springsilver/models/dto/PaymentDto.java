package com.example.springsilver.models.dto;

import com.example.springsilver.models.enumeration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private PaymentMethod paymentMethod;
    private Double amount;
}

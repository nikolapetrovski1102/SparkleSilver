package com.example.springsilver.models.dto;

import com.example.springsilver.models.Product;
import com.example.springsilver.models.enumeration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private PaymentMethod paymentMethod;
    private String address;

}
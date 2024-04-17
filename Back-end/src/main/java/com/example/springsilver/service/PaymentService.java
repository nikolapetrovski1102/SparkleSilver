package com.example.springsilver.service;

import com.example.springsilver.models.Payment;
import com.example.springsilver.models.enumeration.PaymentMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Optional<Payment> save(LocalDate payment_date, PaymentMethod paymentMethod, Double amount);
    List<Payment> findAll();
    Optional<Payment> findById(Long id);
    Optional<Payment> findByPaymentDate(LocalDate date);
    void deleteById(Long id);
}
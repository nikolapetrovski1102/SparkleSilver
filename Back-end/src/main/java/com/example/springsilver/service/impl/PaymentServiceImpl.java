package com.example.springsilver.service.impl;

import com.example.springsilver.models.Payment;
import com.example.springsilver.models.enumeration.PaymentMethod;
import com.example.springsilver.repository.PaymentRepository;
import com.example.springsilver.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }



    @Override
    public Optional<Payment> save(LocalDate payment_date, PaymentMethod paymentMethod, Double amount) {
        Payment payment=new Payment(payment_date,paymentMethod,amount);
        return Optional.of(paymentRepository.save(payment));
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Optional<Payment> findByPaymentDate(LocalDate date) {
        return paymentRepository.findByPaymentDate(date);
    }

    @Override
    public void deleteById(Long id) {
         paymentRepository.deleteById(id);
    }
}

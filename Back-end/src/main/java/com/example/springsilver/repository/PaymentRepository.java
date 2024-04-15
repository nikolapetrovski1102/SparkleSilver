package com.example.springsilver.repository;

import com.example.springsilver.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByPayment_date(LocalDate date);
}

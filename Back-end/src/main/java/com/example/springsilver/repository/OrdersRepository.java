package com.example.springsilver.repository;

import com.example.springsilver.models.Orders;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    Optional<Orders> findByOrder_data(LocalDate date);
}

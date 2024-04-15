package com.example.springsilver.repository;

import com.example.springsilver.models.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping,Long> {
}

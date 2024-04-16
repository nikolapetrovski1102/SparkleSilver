package com.example.springsilver.repository;

import com.example.springsilver.models.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {

    Optional<Shipping> findByShipmentDate(LocalDate shipmentDate);
}

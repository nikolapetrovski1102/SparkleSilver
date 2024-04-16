package com.example.springsilver.service;


import com.example.springsilver.models.Shipping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShippingService {

    Optional<Shipping> save(LocalDate shipment_date, String address);
    List<Shipping> findAll();
    Optional<Shipping> findById(Long id);
    Optional<Shipping> findByShippingDate(LocalDate shippingDate);
    Optional<Shipping> edit(Long id,LocalDate shipment_date, String address);
    void deleteById(Long id);

}

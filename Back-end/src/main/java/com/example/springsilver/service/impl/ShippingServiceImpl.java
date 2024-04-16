package com.example.springsilver.service.impl;

import com.example.springsilver.models.Shipping;
import com.example.springsilver.models.exceptions.ShippingNotFoundException;
import com.example.springsilver.repository.ShippingRepository;
import com.example.springsilver.service.ShippingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingServiceImpl(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Override
    public Optional<Shipping> save(LocalDate shipment_date, String address) {
        Shipping shipping=new Shipping(shipment_date,address);
        this.shippingRepository.save(shipping);
        return Optional.of(shipping);
    }

    @Override
    public List<Shipping> findAll() {
        return this.shippingRepository.findAll();
    }

    @Override
    public Optional<Shipping> findById(Long id) {
        return this.shippingRepository.findById(id);
    }

    @Override
    public Optional<Shipping> findByShippingDate(LocalDate shippingDate) {
        return shippingRepository.findByShipmentDate(shippingDate);
    }

    @Override
    public Optional<Shipping> edit(Long id, LocalDate shipment_date, String address) {
        Shipping shipping=this.shippingRepository.findById(id).orElseThrow(()->new ShippingNotFoundException(id));

        shipping.setShipmentDate(shipment_date);
        shipping.setAddress(address);
        this.shippingRepository.save(shipping);
        return Optional.of(shipping);
    }

    @Override
    public void deleteById(Long id) {
        this.shippingRepository.deleteById(id);
    }
}

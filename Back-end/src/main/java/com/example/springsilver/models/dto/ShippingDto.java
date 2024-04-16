package com.example.springsilver.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShippingDto {
    private Long shippingId;
    private LocalDate shipmentDate;
    private String address;

    public ShippingDto() {
    }

    public ShippingDto(Long shippingId, LocalDate shipmentDate, String address) {
        this.shippingId = shippingId;
        this.shipmentDate = shipmentDate;
        this.address = address;
    }
}
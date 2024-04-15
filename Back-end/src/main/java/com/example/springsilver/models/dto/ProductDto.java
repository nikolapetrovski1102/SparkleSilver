package com.example.springsilver.models.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long quantity;
    private String name;
    private String description;
    private float price;
    private Long category;


    public ProductDto() {}

    public ProductDto(Long quantity, String name, String description, float price, Long category) {
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}

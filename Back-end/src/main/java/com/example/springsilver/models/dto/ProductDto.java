package com.example.springsilver.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long quantity;
    private String name;
    private String description;
    private float price;
    private Long category;
    private String imagePathURL;

    public ProductDto(Long quantity, String name, String description, float price, Long category, String imagePathURL) {
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePathURL = imagePathURL;
    }
}

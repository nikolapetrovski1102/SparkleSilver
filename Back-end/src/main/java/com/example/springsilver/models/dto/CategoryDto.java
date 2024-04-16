package com.example.springsilver.models.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private String categoryName;

    public CategoryDto() {
    }

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }
}

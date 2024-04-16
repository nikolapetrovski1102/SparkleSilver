package com.example.springsilver.models.dto;

import com.example.springsilver.models.Product;
import lombok.Data;

@Data
public class OrderItemDto {
    private int quantity;
    private float price;
    private Long product;
    private Long order;

    public OrderItemDto() {
    }

    public OrderItemDto(int quantity, float price, Long product, Long order) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.order = order;
    }

}



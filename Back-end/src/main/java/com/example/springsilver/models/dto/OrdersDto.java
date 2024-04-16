package com.example.springsilver.models.dto;

import com.example.springsilver.models.OrderItem;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrdersDto {

    private LocalDate orderDate;
    private Double total_price;
    private List<Long> order_items;
    private Long payment;
    private Long shipping;

    public OrdersDto() {
    }

    public OrdersDto(LocalDate orderDate, Double total_price, List<Long> order_items, Long payment, Long shipping) {
        this.orderDate = orderDate;
        this.total_price = total_price;
        this.order_items = order_items;
        this.payment = payment;
        this.shipping = shipping;
    }
}

package com.example.springsilver.models.dto;


import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Users;
import lombok.Data;
import org.hibernate.query.Order;

import java.util.List;

@Data
public class CartDto {
    private Integer quantity;
    private Long users;
    private List<Long> orderItems;

    public CartDto() {
    }

    public CartDto(Integer quantity, Long users, List<Long> orderItems) {
        this.quantity = quantity;
        this.users = users;
        this.orderItems = orderItems;
    }


}

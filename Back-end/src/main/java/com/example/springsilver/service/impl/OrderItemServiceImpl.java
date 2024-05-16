package com.example.springsilver.service.impl;

import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Orders;
import com.example.springsilver.models.Payment;
import com.example.springsilver.models.Shipping;
import com.example.springsilver.repository.OrderItemRepository;
import com.example.springsilver.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);

    }
}

package com.example.springsilver.service.impl;

import com.example.springsilver.models.Orders;
import com.example.springsilver.repository.OrdersRepository;
import com.example.springsilver.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }
}

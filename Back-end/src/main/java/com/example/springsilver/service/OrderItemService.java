package com.example.springsilver.service;

import com.example.springsilver.models.OrderItem;
import com.example.springsilver.models.Orders;
import com.example.springsilver.models.Payment;
import com.example.springsilver.models.Shipping;

public interface OrderItemService {

    OrderItem save(OrderItem orderItem);

}

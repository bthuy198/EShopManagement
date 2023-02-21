package com.example.customers.Service;

import com.example.customers.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrder();
    Order getOrderById(long id);

    void saveOrder(Order order);
}

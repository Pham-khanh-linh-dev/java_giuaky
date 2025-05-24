package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order save(OrderDTO orderDTO);
    Order update(OrderDTO orderDTO);
    void delete(Long id);
    List<Order> findAll();
    Order findById(Long id);
    List<Order> findByUserEmail(String email);
    void addOrderItem(Order order, OrderItem orderItem);
    void removeOrderItem(Order order, Long orderItemId);
    double calculateOrderTotal(Order order);
}

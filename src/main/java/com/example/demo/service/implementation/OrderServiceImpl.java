package com.example.demo.service.implementation;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Order save(OrderDTO orderDTO) {
        Order order = new Order();
        var user = userRepository.findByEmail(orderDTO.getUserEmail());
        if (user == null) {
            throw new RuntimeException("User not found with email: " + orderDTO.getUserEmail());
        }
        order.setUser(user);
        order.setName(orderDTO.getCustomerName());
        order.setPhone(orderDTO.getCustomerPhone());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        order.setCreatedAt(orderDTO.getOrderDate());
        order.setUpdatedAt(orderDTO.getOrderDate());
        order.setAddress(orderDTO.getShippingAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setNote(orderDTO.getNote());
        return orderRepository.save(order);
    }

    @Override
    public Order update(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);
        if (order != null) {
            order.setName(orderDTO.getCustomerName());
            order.setPhone(orderDTO.getCustomerPhone());
            order.setStatus(orderDTO.getStatus());
            order.setUpdatedAt(new Date());
            order.setAddress(orderDTO.getShippingAddress());
            order.setPaymentMethod(orderDTO.getPaymentMethod());
            order.setNote(orderDTO.getNote());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByUserEmail(String email) {
        return orderRepository.findByUserEmail(email);
    }

    @Override
    public void addOrderItem(Order order, OrderItem orderItem) {
        order.getOrderItems().add(orderItem);
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
    }

    @Override
    public void removeOrderItem(Order order, Long orderItemId) {
        order.getOrderItems().removeIf(item -> item.getId().equals(orderItemId));
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
    }

    @Override
    public double calculateOrderTotal(Order order) {
        return order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();
    }
}

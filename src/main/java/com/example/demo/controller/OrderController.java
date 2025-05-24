package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin-page/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "order_list";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order_detail";
    }

    @GetMapping("/user/{email}")
    public String getOrdersByUser(@PathVariable String email, Model model) {
        List<Order> orders = orderService.findByUserEmail(email);
        model.addAttribute("orders", orders);
        return "order_list";
    }

    @PostMapping("/{id}/status")
    @ResponseBody
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderService.findById(id);
        if (order != null) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setStatus(status);
            orderDTO.setUserEmail(order.getUser().getEmail());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setShippingAddress(order.getAddress());
            orderDTO.setPaymentMethod(order.getPaymentMethod());
            orderDTO.setNote(order.getNote());
            orderService.update(orderDTO);
            return "success";
        }
        return "error";
    }
}

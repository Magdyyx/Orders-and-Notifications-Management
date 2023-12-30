package com.example.FCAI.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FCAI.service.OrderService;

@RestController
@RequestMapping ("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

}

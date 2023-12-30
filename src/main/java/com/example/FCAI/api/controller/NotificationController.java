package com.example.FCAI.api.controller;

import com.example.FCAI.api.model.Message;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.OrderService;
import com.example.FCAI.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private NotificationService notificationService;
    private CustomerService customerService;
    private OrderService orderService;

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  CustomerService customerService,
                                  OrderService orderService) {
        this.notificationService = notificationService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/{language}/{receiveMedium}/{messageType}")
    public ResponseEntity<Message> getMessage(@PathVariable String language,
                                              @PathVariable String receiveMedium,
                                              @PathVariable String messageType,
                                              @RequestBody Order order) {
        Message message = notificationService.sendNotification(order, language, receiveMedium, messageType);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

}

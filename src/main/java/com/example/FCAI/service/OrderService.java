package com.example.FCAI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FCAI.api.Repositories.OrderRepo;
import com.example.FCAI.api.model.Order;

@Service
public class OrderService {
    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public boolean createOrder(Order o) {
        if (orderRepo.findById(o.getId()) != null) {
            return false;
        }

        orderRepo.create(o);
        return true;
    }

    public Order update(Order o) {
        return orderRepo.update(o);
    }

    public boolean delete(int id) {
        Order o = orderRepo.findById(id);
        if (o == null)
            return false;

        orderRepo.delete(o);
        return true;
    }

    public Order findById(int id) {
        return orderRepo.findById(id);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}

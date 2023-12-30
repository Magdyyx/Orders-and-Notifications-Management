package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderRepo implements RepositoryService<Order> {
    private List<Order> orderList;

    public OrderRepo() {
        orderList = new ArrayList<Order>();
    }

    @Override
    public Order create(Order order) {
        orderList.add(order);
        return order;
    }

    @Override
    public Order update(Order oldOrder, Order order) {
        orderList.set(orderList.indexOf(oldOrder), order);
        return order;
    }

    @Override
    public Order delete(Order order) {
        orderList.remove(order);
        return order;
    }

    @Override
    public Order findById(int id) {
        Optional<Order> order = orderList.stream().filter(o -> o.getId() == id).findFirst();
        return order.get();
    }

    @Override
    public List<Order> findAll() {
        return orderList;
    }
}

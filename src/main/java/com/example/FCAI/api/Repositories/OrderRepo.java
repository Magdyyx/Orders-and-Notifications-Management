package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Order.CompositeOrder;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;
import com.example.FCAI.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderRepo implements RepositoryService<Order> {
    private List<Order> orderList;

    public OrderRepo() {
        orderList = new ArrayList<Order>();
//        SimpleOrder order1 = new SimpleOrder(
//                1,
//                28,
//                5.0,
//                "El-Haram",
//                "123 Main St",
//                1,
//                Map.of(1, 1, 2, 1, 3, 1));
//        SimpleOrder order2 = new SimpleOrder(
//                2,
//                11,
//                15,
//                "El-Dokki",
//                "Street2",
//                2,
//                Map.of(2, 2, 3, 1));
//        SimpleOrder order3 = new SimpleOrder(3,
//                40, 7,
//                "El-Dokki",
//                "Street3",
//                3,
//                Map.of(1, 2));
//
//        orderList.addAll(List.of(order1,
//                new CompositeOrder(4, 22, "El-Dokki", null, 2,
//                        List.of(order2, order3))));
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

    public List<SimpleOrder> findAllSimpleOrders(List<Integer> serialNumbers) {
    return null;
    }
}

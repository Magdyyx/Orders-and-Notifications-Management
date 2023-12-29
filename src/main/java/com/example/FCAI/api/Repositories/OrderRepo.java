package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.CompositeOrder;
import com.example.FCAI.api.model.Order;
import com.example.FCAI.api.model.SimpleOrder;
import com.example.FCAI.service.RepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderRepo implements RepositoryService<Order> {
    private List<Order> orderList;

    public OrderRepo() {
        orderList = new ArrayList<Order>();
        SimpleOrder order1 = new SimpleOrder(1, 28, "El-Haram", "Street1", 1,
                Arrays.asList(1, 2, 3));
        SimpleOrder order2 = new SimpleOrder(2, 11, "El-Dokki", "Street2", 2,
                Arrays.asList(2, 2, 3));
        SimpleOrder order3 = new SimpleOrder(3, 40, "El-Dokki", "Street3", 3,
                Arrays.asList(1, 1));

        orderList.addAll(List.of(order1,
                new CompositeOrder(4, 51, "El-Dokki", null, 2,
                        List.of(order2, order3))));
    }

    @Override
    public Order create(Order order) {
        orderList.add(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        Order oldOrder = findById(order.getId());
        if (oldOrder == null)
            return null;

        oldOrder = (order instanceof CompositeOrder)
                ? new CompositeOrder(order.getId(), order.getTotalPrice(), order.getDeliveryDistrict(),
                        order.getDeliveryAddress(), order.getCustomerID(), ((CompositeOrder) order).getOrders())
                : new SimpleOrder(order.getId(), order.getTotalPrice(), order.getDeliveryDistrict(),
                        order.getDeliveryAddress(), order.getCustomerID(), ((SimpleOrder) order).getProductIDs());

        return oldOrder;
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

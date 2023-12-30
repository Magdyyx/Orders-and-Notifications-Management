package com.example.FCAI.api.model;

import com.example.FCAI.api.model.Order.SimpleOrder;

import java.util.List;

public class RequestedCompoundOrder {
    List<SimpleOrder> orders;

    public List<SimpleOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<SimpleOrder> orders) {
        this.orders = orders;
    }

}

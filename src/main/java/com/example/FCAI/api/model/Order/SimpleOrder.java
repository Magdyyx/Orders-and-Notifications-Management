package com.example.FCAI.api.model.Order;

import java.util.Map;

import com.example.FCAI.api.Enums.OrderState;

public class SimpleOrder extends Order {
    Map<Integer, Integer> products;

    public SimpleOrder(double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, boolean canCancelOrder, OrderState state, Map<Integer, Integer> products) {
        super(totalPrice, shippingFee, deliveryDistrict, deliveryAddress, customerID, canCancelOrder, state);
        this.products = products;
    }

    @Override
    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return String.format("Simple Order: {0}", this);
    }
}

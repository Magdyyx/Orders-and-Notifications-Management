package com.example.FCAI.api.model;

import java.util.Map;

public class SimpleOrder extends Order {
    Map<Integer, Integer> products;

    public SimpleOrder(int id, double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, Map<Integer, Integer> products) {
        super(id, totalPrice, shippingFee, deliveryDistrict, deliveryAddress, customerID);
        this.products = products;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    @Override
    public String details() {
        throw new UnsupportedOperationException("Unimplemented method 'details'");
    }
}

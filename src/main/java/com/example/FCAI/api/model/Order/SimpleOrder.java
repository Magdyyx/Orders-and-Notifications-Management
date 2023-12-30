package com.example.FCAI.api.model.Order;

import java.util.Map;

public class SimpleOrder extends Order {
    Map<Integer, Integer> products;
    double totalPrice;

    public SimpleOrder(double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, Map<Integer, Integer> products) {
        super(shippingFee, deliveryDistrict, deliveryAddress, customerID);
        this.products = products;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public double getShippingFee() {
        return shippingFee;
    }

    @Override
    public String details() {
        throw new UnsupportedOperationException("Unimplemented method 'details'");
    }
}

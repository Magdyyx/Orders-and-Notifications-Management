package com.example.FCAI.api.model;

import java.util.List;

public class SimpleOrder extends Order {
    List<Integer> productIDs;

    public SimpleOrder(int id, double totalPrice, String district, String deliveryAddress, int customerID,
            List<Integer> products) {
        super(id, totalPrice, district, deliveryAddress, customerID);
        this.productIDs = products;
    }

    public List<Integer> getProductIDs() {
        return productIDs;
    }

    public void setProducts(List<Integer> products) {
        this.productIDs = products;
    }

    @Override
    public String details() {
        throw new UnsupportedOperationException("Unimplemented method 'details'");
    }
}

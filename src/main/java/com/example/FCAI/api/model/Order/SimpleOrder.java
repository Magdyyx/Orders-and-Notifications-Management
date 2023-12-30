package com.example.FCAI.api.model.Order;

import com.example.FCAI.api.model.Product;

import java.util.List;
import java.util.Map;

public class SimpleOrder extends Order {
    List<Product> products;

    public SimpleOrder(int id, double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, List<Product> products) {
        super(id, shippingFee, deliveryDistrict, deliveryAddress, customerID);
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }



    public void setProducts(List<Product> products) {
        this.products = products;
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

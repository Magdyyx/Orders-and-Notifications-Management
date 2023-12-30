package com.example.FCAI.api.model.Order;

import com.example.FCAI.api.model.Product;

import java.util.List;
import java.util.Map;

public class SimpleOrder extends Order {
    List<Product> products;

    Map<Integer, Integer> productsQuantity;

    public Map<Integer, Integer> getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(Map<Integer, Integer> productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public SimpleOrder(double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, List<Product> products) {
        super( shippingFee, deliveryDistrict, deliveryAddress, customerID);
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

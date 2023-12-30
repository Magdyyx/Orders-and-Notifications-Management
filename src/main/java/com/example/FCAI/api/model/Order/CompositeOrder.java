package com.example.FCAI.api.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeOrder extends Order {

    List<Order> orders;

    public CompositeOrder(double totalPrice, double shippingFee, String deliveryDistrict,
            String deliveryAddress, int customerID, List<Order> orders) {
        super(totalPrice, shippingFee, deliveryDistrict, deliveryAddress, customerID);
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Map<Integer, Integer> getProducts() {
        Map<Integer, Integer> totalProducts = new HashMap<>();

        for (var order : orders) {
            Map<Integer, Integer> products = order.getProducts();
            for (Map.Entry<Integer, Integer> product : products.entrySet()) {
                if (totalProducts.containsKey(product.getKey())) {
                    totalProducts.put(product.getKey(), totalProducts.get(product.getKey()) + product.getValue());
                } else {
                    totalProducts.put(product.getKey(), product.getValue());
                }
            }
        }
        return totalProducts;
    }

    public int getNumOfChildren() {
        return orders.size();
    }

    @Override
    public String toString() {
        return String.format("Compound Order: {0}", this);
    }
}

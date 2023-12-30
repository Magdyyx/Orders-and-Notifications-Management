package com.example.FCAI.api.model.Order;

import java.util.List;

public class CompositeOrder extends Order {
    List<Order> orders;

    public CompositeOrder(int id, double shippingFee, String deliveryDistrict,
            String deliveryAddress, int customerID, List<Order> orders) {
        super(id, shippingFee, deliveryDistrict, deliveryAddress, customerID);
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    @Override
//    public void add(Order order) {
//        orders.add(order);
//    }
//
//    @Override
//    public void remove(Order order) {
//        order.remove(order);
//    }
//
//    @Override
//    public Order getChild(int i) {
//        return (Order) orders.get(i);
//    }

    @Override
    public double getShippingFee() {
        return shippingFee;
    }

    public int getNumOfChildren(){
        return orders.size();
    }

    @Override
    public String details() {
        throw new UnsupportedOperationException("Unimplemented method 'details'");
    }
}

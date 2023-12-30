package com.example.FCAI.api.model;

import java.util.Map;

public class RequestedSimpleOrder {
    Map<Integer,Integer> productsAndQuantity;
    String deliveryDistrict;
    String deliveryAddress;

    int customerID;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Map<Integer, Integer> getProductsAndQuantity() {
        return productsAndQuantity;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setProductsAndQuantity(Map<Integer, Integer> productsAndQuantity) {
        this.productsAndQuantity = productsAndQuantity;
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public RequestedSimpleOrder(Map<Integer, Integer> productsAndQuantity, String deliveryDistrict, String deliveryAddress, int customerID) {
        this.customerID = customerID;
        this.productsAndQuantity = productsAndQuantity;
        this.deliveryDistrict = deliveryDistrict;
        this.deliveryAddress = deliveryAddress;
    }
}

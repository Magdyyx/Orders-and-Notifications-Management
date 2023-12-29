package com.example.FCAI.api.model;

public abstract class Order {
    protected int id;
    protected double totalPrice;
    protected String deliveryDistrict;
    protected String deliveryAddress;
    protected int customerID;

    public Order(int id, double totalPrice, String district, String deliveryAddress, int customerID) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.deliveryDistrict = district;
        this.deliveryAddress = deliveryAddress;
        this.customerID = customerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    public void setDeliveryDistrict(String deliveryLocation) {
        this.deliveryDistrict = deliveryLocation;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public abstract String details();
}

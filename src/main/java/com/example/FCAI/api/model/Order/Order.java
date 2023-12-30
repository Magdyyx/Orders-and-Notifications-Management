package com.example.FCAI.api.model.Order;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleOrder.class, name = "SimpleOrder"),
        @JsonSubTypes.Type(value = CompositeOrder.class, name = "CompositeOrder")
})
public abstract class Order {
    protected static int idCounter = 0;

    protected int id;
    protected double price;
    protected double shippingFee;
    protected String deliveryDistrict;
    protected String deliveryAddress;
    protected int customerID;

    public Order(double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID) {
        this.id = idCounter++;
        this.shippingFee = shippingFee;
        this.deliveryDistrict = deliveryDistrict;
        this.deliveryAddress = deliveryAddress;
        this.customerID = customerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public abstract double getShippingFee();

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public abstract String details();

}

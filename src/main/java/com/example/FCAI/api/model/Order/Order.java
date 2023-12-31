package com.example.FCAI.api.model.Order;

import com.example.FCAI.api.Enums.OrderState;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleOrder.class, name = "SimpleOrder"),
        @JsonSubTypes.Type(value = CompositeOrder.class, name = "CompositeOrder")
})
public abstract class Order {
    protected static int idCounter = 0;
    protected static final double shippingFeeCost = 20;

    protected int id;
    protected double totalPrice;
    protected double shippingFee;
    protected String deliveryDistrict;
    protected String deliveryAddress;
    protected int customerID;
    protected boolean canCancelOrder;
    protected OrderState state;

    public Order(double totalPrice, double shippingFee, String deliveryDistrict, String deliveryAddress,
            int customerID, boolean canCancelOrder, OrderState state) {
        this.id = idCounter++;
        this.shippingFee = shippingFee;
        this.deliveryDistrict = deliveryDistrict;
        this.deliveryAddress = deliveryAddress;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
        this.canCancelOrder = canCancelOrder;
        this.state = state;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public static double getShippingFeeCost() {
        return shippingFeeCost;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public boolean CanCancelOrder() {
        return canCancelOrder;
    }

    public void setCanCancelOrder(boolean canCancelOrder) {
        this.canCancelOrder = canCancelOrder;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public abstract String toString();
    public abstract Map<Integer, Integer> getProducts();
}

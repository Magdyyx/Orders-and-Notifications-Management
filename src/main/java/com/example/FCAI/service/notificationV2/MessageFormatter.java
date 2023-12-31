package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;
import com.example.FCAI.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageFormatter {
    Message message;
    List<Product> productList;
    String orderId;
    String customer;
    String orderDistrict;
    String orderLocation;


    //public void printMessage(){}

    public Message formatMessage() {
        stringFormat();
        return message;
    }

    public abstract void stringFormat();
    public abstract void setMessageType();
    public abstract void setMessageLanguage();

    public String getProductsMessage(){
        StringBuilder productsMessage = new StringBuilder();
        for (Product product : productList) {
            productsMessage.append(product.getName() + ", ");
        }

        return productsMessage.toString();
    }


    public String getOrderDistrict() {
        return orderDistrict;
    }

    public void setOrderDistrict(String orderDistrict) {
        this.orderDistrict = orderDistrict;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

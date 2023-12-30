package com.example.FCAI.api.model.Notification;

import com.example.FCAI.api.model.Order.Order;

public class Notification {

    private Order order;
    private Message msg;

    public Notification(Order order, Message msg) {
        this.order = order;
        this.msg = msg;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}

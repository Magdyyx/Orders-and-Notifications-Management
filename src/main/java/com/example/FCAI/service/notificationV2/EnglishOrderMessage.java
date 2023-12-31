package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class EnglishOrderMessage extends MessageFormatter {
    public EnglishOrderMessage(){

    }


    @Override
    public Message formatMessage() {
        message.setMessage("Dear " + customer + ", " + "your booking of " + getProductsMessage()
                + "is confirmed with an order id: " + getOrderId() +"\nthanks for using our store :)");
        return message;
    }
}
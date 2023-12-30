package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class EnglishShipmentMessage extends MessageFormatter{
    public EnglishShipmentMessage(){

    }


    @Override
    public Message formatMessage() {
        message.setMessage("Dear " + customer + ", " + " your order of "
                + getProductsMessage() + "with id: " + getOrderId() +" has been delivered to " + getOrderDistrict() + ", "
                + getOrderLocation() + ".\nthanks for using our store :)");
        return message;
    }
}

package com.example.FCAI.service.notificationService.MessageFacility;

public class EnglishOrderMessage extends MessageFormatter {
    public EnglishOrderMessage(){

    }


    @Override
    public void stringFormat() {
        message.setMessage("Dear " + customer + ", " + "your booking of " + getProductsMessage()
                + "is confirmed with an order id: " + getOrderId() +"\nthanks for using our store :)");
    }

    @Override
    public void setMessageType() {
        message.setMessageType("order");
    }

    @Override
    public void setMessageLanguage() {
        message.setMessageLanguage("english");
    }
}

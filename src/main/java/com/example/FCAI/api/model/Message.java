package com.example.FCAI.api.model;

public class Message {
    String message;
    int messageId;
    int customerId;

    public Message(int customerId, int messageId){
        this.messageId = messageId;
        this.customerId = customerId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Message(Message message1) {
        this.message = message1.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}

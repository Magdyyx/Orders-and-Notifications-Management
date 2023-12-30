package com.example.FCAI.api.model;

public class Message {
    String message;

    public Message(){

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
}

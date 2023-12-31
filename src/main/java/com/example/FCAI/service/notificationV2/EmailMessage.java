package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class EmailMessage extends ReceiveMediumDecorator{
    @Override public void stringFormat(){
        message.setMessage("Sending Email message:\n" + messageFormatter.formatMessage().getMessage());
    }

    @Override
    public void setMessageType() {
        messageFormatter.setMessageType();
    }

    @Override
    public void setMessageLanguage() {
        messageFormatter.setMessageLanguage();
    }

    @Override
    public void setMessageMedium(){
        message.setMessageMedium("email");
    }


    /*
    *
    *     @Override
    public Message formatMessage() {
        return message;
    }

    @Override
    public Message stringFormat() {
        message.setMessage("Sending SMS message:\n" + messageFormatter.formatMessage().getMessage());
    }

    @Override
    public void setMessageMedium() {
        message.setMessageMedium("sms");
    }*/
//    @Override
//    public void getFormattedMessage() {
//        System.out.println(formatMessage() + messageFormatter.formatMessage());
//    }
}

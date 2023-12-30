package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class SMSMessage extends ReceiveMediumDecorator{
    @Override
    public Message formatMessage() {
        message.setMessage("Sending SMS message:\n" + messageFormatter.formatMessage().getMessage());
        return message;
    }

//    @Override
//    public Message getFormattedMessage() {
//        System.out.println(formatMessage() + messageFormatter.formatMessage());
//    }
}

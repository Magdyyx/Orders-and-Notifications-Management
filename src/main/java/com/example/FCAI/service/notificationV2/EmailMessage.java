package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public class EmailMessage extends ReceiveMediumDecorator{
    @Override
    public Message formatMessage() {
        message.setMessage("Sending Email message:\n" + messageFormatter.formatMessage().getMessage());
        return message;
    }

//    @Override
//    public void getFormattedMessage() {
//        System.out.println(formatMessage() + messageFormatter.formatMessage());
//    }
}

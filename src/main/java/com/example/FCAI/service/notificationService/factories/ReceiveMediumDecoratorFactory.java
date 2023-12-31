package com.example.FCAI.service.notificationService.factories;

import com.example.FCAI.service.notificationService.MessageFacility.EmailMessage;
import com.example.FCAI.service.notificationService.MessageFacility.ReceiveMediumDecorator;
import com.example.FCAI.service.notificationService.MessageFacility.SMSMessage;

public class ReceiveMediumDecoratorFactory {
    ReceiveMediumDecorator receiveMediumDecorator;

    public ReceiveMediumDecoratorFactory(){
        receiveMediumDecorator = null;

    }


    public ReceiveMediumDecorator createReceiveMedium(String receiveMedium) {
        if (receiveMedium.toLowerCase().equals("sms")){
            receiveMediumDecorator = new SMSMessage();
        } else if (receiveMedium.toLowerCase().equals("email")) {
            receiveMediumDecorator = new EmailMessage();
        }
        return receiveMediumDecorator;
    }
}

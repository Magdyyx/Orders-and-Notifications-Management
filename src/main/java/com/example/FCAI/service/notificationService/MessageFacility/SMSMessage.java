package com.example.FCAI.service.notificationService.MessageFacility;

public class SMSMessage extends ReceiveMediumDecorator{
//    @Override
//    public Message formatMessage() {
//        return message;
//    }

    @Override
    public void stringFormat() {
        message.setMessage("Sending SMS message:\n" + messageFormatter.formatMessage().getMessage());
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
    public void setMessageMedium() {
        message.setMessageMedium("sms");
    }

//    @Override
//    public Message getFormattedMessage() {
//        System.out.println(formatMessage() + messageFormatter.formatMessage());
//    }
}

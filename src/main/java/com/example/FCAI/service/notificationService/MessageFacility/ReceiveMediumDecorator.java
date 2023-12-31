package com.example.FCAI.service.notificationService.MessageFacility;

import com.example.FCAI.api.model.Message;

public abstract class ReceiveMediumDecorator extends MessageFormatter {
    MessageFormatter messageFormatter;

    public Message formatMessage(){
        stringFormat();
        setMessageMedium();
        setMessageType();
        setMessageLanguage();
        return message;
    }

    public abstract void stringFormat();

    public abstract void setMessageMedium();

//    public Message getMessage() {
//        return message;
//    }

    public void setMessage(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }
}

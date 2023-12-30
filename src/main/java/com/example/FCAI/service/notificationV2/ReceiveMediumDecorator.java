package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Message;

public abstract class ReceiveMediumDecorator extends MessageFormatter {
    MessageFormatter messageFormatter;

    public abstract Message formatMessage();


//    public Message getMessage() {
//        return message;
//    }

    public void setMessage(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }
}

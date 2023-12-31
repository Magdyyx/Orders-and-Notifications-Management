package com.example.FCAI.service.notificationService.factories;

import com.example.FCAI.service.notificationService.MessageFacility.*;

public class MessageFormatterFactory {
    MessageFormatter messageFormatter;

    public MessageFormatterFactory() {
        messageFormatter = null;
    }

    public MessageFormatter createMessageFormatter(String language, String messageType){
        if (messageType.toLowerCase().equals("order")) {
            if (language.toLowerCase().equals("arabic")) {
                messageFormatter = new ArabicOrderMessage();
            } else if (language.toLowerCase().equals("english")) {
                messageFormatter = new EnglishOrderMessage();
            }
        } else if (messageType.toLowerCase().equals("shipment")) {
            if (language.toLowerCase().equals("arabic")) {
                messageFormatter = new ArabicShipmentMessage();
            } else if (language.toLowerCase().equals("english")) {
                messageFormatter = new EnglishShipmentMessage();
            }
        }
        return messageFormatter;
    }
}

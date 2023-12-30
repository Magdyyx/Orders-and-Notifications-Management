package com.example.FCAI.service.notification;

import com.example.FCAI.api.model.Product;

import java.util.List;

public class ReceiveMedium {
    SystemMessage systemMessage;

    public boolean send(List<Product> purchasedProducts, String customerName,String messageType, String language){


        return false;
    }

    private SystemMessage systemMessageFactory(String messageType) {
        SystemMessage message = null;
        if (message.equals("order")) {
            message = new OrderMessage();
        } else if (message.equals("shipment")) {
            message = new ShipmentMessage();
        }
        return message;
    }
}

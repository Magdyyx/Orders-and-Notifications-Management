package com.example.FCAI.service;

import com.example.FCAI.api.model.Message;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.notificationV2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public NotificationService(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    public Message sendNotification(Order order, String language, String receiveMedium, String messageType) {
        ReceiveMediumDecorator receiveMediumDecorator = receiveMediumFactory(receiveMedium);
        MessageFormatter messageFormatter = messageFormatterFactory(language, messageType);
        List<Product> productList = productService.getProducts()

        Message message = new Message();


        // TODO: zbt di
        return null;
    }



    private ReceiveMediumDecorator receiveMediumFactory(String receiveMedium) {
        ReceiveMediumDecorator receiveMediumDecorator = null;
        if (receiveMedium.toLowerCase().equals("sms")){
            receiveMediumDecorator = new SMSMessage();
        } else if (receiveMedium.toLowerCase().equals("email")) {
            receiveMediumDecorator = new EmailMessage();
        }
        return receiveMediumDecorator;
    }

    private MessageFormatter messageFormatterFactory(String language, String messageType){
        MessageFormatter messageFormatter = null;
        if (messageType.toLowerCase().equals("order")) {
            if (language.toLowerCase().equals("arabic")) {
                messageFormatter = new ArabicOrderMessage();
            } else if (messageType.toLowerCase().equals("english")) {
                messageFormatter = new EnglishOrderMessage();
            }
        } else if (messageType.toLowerCase().equals("shipment")) {
            if (language.toLowerCase().equals("arabic")) {
                messageFormatter = new ArabicShipmentMessage();
            } else if (messageType.toLowerCase().equals("english")) {
                messageFormatter = new EnglishShipmentMessage();
            }
        }

        return messageFormatter;
    }
}



package com.example.FCAI.service;

import com.example.FCAI.api.Repositories.MessageRepo;
import com.example.FCAI.api.model.Message;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.notificationV2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public class NotificationService {
    private MessageRepo messageRepo;

    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public NotificationService(MessageRepo messageRepo, CustomerService customerService, ProductService productService) {
        this.messageRepo = messageRepo;
        this.customerService = customerService;
        this.productService = productService;
    }

    public Message sendNotification(Order order, String language, String receiveMedium, String messageType) {
        ReceiveMediumDecorator receiveMediumDecorator = receiveMediumFactory(receiveMedium);
        MessageFormatter messageFormatter = messageFormatterFactory(language, messageType);
        Map<Integer, Integer> productsMap = order.getProducts();
        List<Product> productList = productService.getProducts(productsMap);
        Message message = new Message(order.getCustomerID(), 1);

        receiveMediumDecorator.setMessage(message);
        messageFormatter.setProductList(productList);
        messageFormatter.setCustomer(customerService.getCustomer(order.getCustomerID()).getName());
        messageFormatter.setOrderDistrict(order.getDeliveryDistrict());
        messageFormatter.setOrderLocation(order.getDeliveryAddress());
        messageFormatter.setMessage(message);
        messageFormatter.setOrderId(String.valueOf(order.getId()));
        receiveMediumDecorator.setMessage(messageFormatter);
        receiveMediumDecorator.formatMessage();

        Thread messageScheduler = new Thread(new MessageScheduler(message, this));
        messageScheduler.start();

        return message;

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

    public Message addToMessageQueue(Message message) {
        return messageRepo.create(message);
    }
    public Message sendMessage(Message message) {
        messageRepo.delete(message);
        return messageRepo.setSent(message);
    }

    public int messageQueueSize(){
        Queue<Message> queue = messageRepo.getMessagesQueue();
        return queue.size();
    }
}



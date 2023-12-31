package com.example.FCAI.service.notificationService;

import com.example.FCAI.api.Repositories.MessageRepo;
import com.example.FCAI.api.model.Message;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.ProductService;
import com.example.FCAI.service.notificationService.*;
import com.example.FCAI.service.notificationService.MessageFacility.MessageFormatter;
import com.example.FCAI.service.notificationService.MessageFacility.ReceiveMediumDecorator;
import com.example.FCAI.service.notificationService.factories.MessageFormatterFactory;
import com.example.FCAI.service.notificationService.factories.ReceiveMediumDecoratorFactory;
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
        ReceiveMediumDecoratorFactory receiveMediumDecoratorFactory = new ReceiveMediumDecoratorFactory();
        ReceiveMediumDecorator receiveMediumDecorator = receiveMediumDecoratorFactory.createReceiveMedium(receiveMedium);

        MessageFormatterFactory messageFormatterFactory = new MessageFormatterFactory();
        MessageFormatter messageFormatter = messageFormatterFactory.createMessageFormatter(language, messageType);


        Message message = new Message(order.getCustomerID(), 1);
        receiveMediumDecorator.setMessage(message);

        Map<Integer, Integer> productsMap = order.getProducts();
        List<Product> productList = productService.getProducts(productsMap);
        String customerName = customerService.getCustomer(order.getCustomerID()).getName();
        String orderDistrict = order.getDeliveryDistrict();
        String orderLocation = order.getDeliveryAddress();
        String orderID = String.valueOf(order.getId());



        setFormatterData(messageFormatter, message, productList, customerName, orderDistrict, orderLocation,
                orderID);

        receiveMediumDecorator.setMessage(messageFormatter);


        receiveMediumDecorator.formatMessage();


        // simulating time between ordering and shipment
        Thread messageScheduler = new Thread(new MessageScheduler(message, this));
        messageScheduler.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message;
    }
    private void setFormatterData(MessageFormatter messageFormatter, Message message,
                                  List<Product> productList, String customerName,
                                  String orderDistrict, String orderLocation, String orderID) {
        messageFormatter.setMessage(message);
        messageFormatter.setProductList(productList);
        messageFormatter.setCustomer(customerName);
        messageFormatter.setOrderDistrict(orderDistrict);
        messageFormatter.setOrderLocation(orderLocation);
        messageFormatter.setOrderId(orderID);

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

    public int getMostNotifiedCustomerID(){
        Queue<Message> sentMessages = messageRepo.getSentMessages();
        int mostNotifiedCustomerId = -1;
        int maxCounter = -1;
        for (Message currentMessage : sentMessages) {
            int counter = 0;
            for (Message message : sentMessages) {
                if (currentMessage.getCustomerId() == message.getCustomerId()) {
                    counter++;
                }
            }
            if (counter > maxCounter) {
                maxCounter = counter;
                mostNotifiedCustomerId = currentMessage.getCustomerId();
            }
        }
        return mostNotifiedCustomerId;
    }

    public String getMostSentNotificationTemplate(){
        Queue<Message> sentMessages = messageRepo.getSentMessages();
        String mostSentNotificationTemplate = null;
        int maxCounter = -1;
        for (Message currentMessage : sentMessages) {
            int counter = 0;
            for (Message message : sentMessages) {
                if (currentMessage.getMessageType().equals(message.getMessageType())) {
                    counter++;
                }
            }
            if (counter > maxCounter) {
                maxCounter = counter;
                mostSentNotificationTemplate = currentMessage.getMessageType();
            }
        }

        return mostSentNotificationTemplate;
    }

    public Queue<Message> getAllNotifications() {
        return messageRepo.getSentMessages();
    }
}



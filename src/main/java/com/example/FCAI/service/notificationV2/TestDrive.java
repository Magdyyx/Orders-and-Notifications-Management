package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.Enums.OrderState;
import com.example.FCAI.api.Repositories.CustomerRepo;
import com.example.FCAI.api.Repositories.MessageRepo;
import com.example.FCAI.api.Repositories.OrderRepo;
import com.example.FCAI.api.Repositories.ProductRepo;
import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.NotificationService;
import com.example.FCAI.service.OrderService;
import com.example.FCAI.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDrive {

    public static void main(String[] args) {
        // SMSMessage smsMessage = new SMSMessage();
        // ArabicOrderMessage arabicOrderMessage = new ArabicOrderMessage();
        // smsMessage.setMessage(arabicOrderMessage);
        //
        // List<Product> products = new ArrayList<>();
        // Product product1 = new Product("mega", 1, "nestle", "ice cream", 20, 20);
        // Product product2 = new Product("hohos", 2, "vendor2", "cake", 3, 60);
        // Product product3 = new Product("big chips", 3, "egypt foods", "chipsy", 5,
        // 40);
        // products.addAll(List.of(product1, product2, product3));
        //
        // //englishOrderMessage.setCustomer("Daddy");
        // //englishOrderMessage.setProductList(products);
        // arabicOrderMessage.setCustomer("Daddy");
        // arabicOrderMessage.setOrderDistrict("Nigga land");
        // arabicOrderMessage.setOrderLocation("a7a street");
        // arabicOrderMessage.setProductList(products);
        // System.out.println(smsMessage.formatMessage().getMessage());
        //
        // SMSMessage smsMessage1 = new SMSMessage();
        // EnglishShipmentMessage englishShipmentMessage = new EnglishShipmentMessage();
        // englishShipmentMessage.setCustomer("Yousef");
        // englishShipmentMessage.setOrderDistrict("nigga land");
        // englishShipmentMessage.setOrderLocation("asdfsf");
        // englishShipmentMessage.setProductList(products);
        // englishShipmentMessage.setOrderId("123");
        // smsMessage1.setMessage(englishShipmentMessage);
        // System.out.println(smsMessage1.formatMessage().getMessage());

        MessageRepo messageRepo = new MessageRepo();
        CustomerRepo customerRepo = new CustomerRepo();
        CustomerService customerService = new CustomerService(customerRepo);
        ProductRepo productRepo = new ProductRepo();
        ProductService productService = new ProductService(productRepo);

        Product product1 = new Product("hohos", 1, "sbaho", "asdf", 2, 60);
        Product product2 = new Product("hohos2", 2, "sbaho", "asdf", 2, 60);
        Product product3 = new Product("hohos3", 3, "sbaho", "asdf", 2, 60);
        Product product4 = new Product("hohos4", 4, "sbaho", "asdf", 2, 60);

        productService.createProduct(product1);
        productService.createProduct(product2);
        productService.createProduct(product3);

        Customer customer = new Customer(2, "Daddy", 300, "Al Haram", "sba7o street");
        customerService.createCustomer(customer);

        Order order = new SimpleOrder(50, 5, "Al Haram", "sba7o street",
                2, true, OrderState.Placed, Map.of(1, 1, 2, 1, 3, 1));
        OrderRepo orderRepo = new OrderRepo();
        OrderService orderService = new OrderService(orderRepo, customerService, productService);
        orderService.placeSimpleOrder(customer, Map.of(1, 1, 2, 1, 3, 1));

        Order order1 = new SimpleOrder(0, 0, "", "", 1, true, OrderState.Placed, Map.of(0, 0));

        NotificationService notificationService = new NotificationService(messageRepo, customerService, productService);
        System.out.println("Testing NotificationService");
        System.out.println(notificationService.sendNotification(order1, "arabic", "email", "shipment").getMessage());
    }
}

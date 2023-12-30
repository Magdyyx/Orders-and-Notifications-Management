package com.example.FCAI.service.notificationV2;

import com.example.FCAI.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class TestDrive {

    public static void main(String[] args) {
        SMSMessage smsMessage = new SMSMessage();
        ArabicOrderMessage arabicOrderMessage = new ArabicOrderMessage();
        EnglishOrderMessage englishOrderMessage = new EnglishOrderMessage();
        smsMessage.setMessage(arabicOrderMessage);

        List<Product> products = new ArrayList<>();
        Product product1 = new Product("mega", 1, "nestle", "ice cream", 20, 20);
        Product product2 = new Product("hohos", 2, "vendor2", "cake", 3, 60);
        Product product3 = new Product("big chips", 3, "egypt foods", "chipsy", 5, 40);
        products.addAll(List.of(product1, product2, product3));

        //englishOrderMessage.setCustomer("Daddy");
        //englishOrderMessage.setProductList(products);
        arabicOrderMessage.setCustomer("Daddy");
        arabicOrderMessage.setOrderDistrict("Nigga land");
        arabicOrderMessage.setOrderLocation("a7a street");
        arabicOrderMessage.setProductList(products);
        System.out.println(smsMessage.formatMessage().getMessage());

        SMSMessage smsMessage1 = new SMSMessage();
        EnglishShipmentMessage englishShipmentMessage = new EnglishShipmentMessage();
        englishShipmentMessage.setCustomer("Yousef");
        englishShipmentMessage.setOrderDistrict("nigga land");
        englishShipmentMessage.setOrderLocation("asdfsf");
        englishShipmentMessage.setProductList(products);
        englishShipmentMessage.setOrderId("123");
        smsMessage1.setMessage(englishShipmentMessage);
        System.out.println(smsMessage1.formatMessage().getMessage());

    }
}

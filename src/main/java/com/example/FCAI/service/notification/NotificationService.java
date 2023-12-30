//package com.example.FCAI.service.notification;
//
//import com.example.FCAI.api.model.Order.Order;
//import com.example.FCAI.api.model.Product;
//
//import java.util.List;
//
//public class NotificationService {
//    ReceiveMedium receiveMedium;
//
//
//    public boolean send(Order order, String receiveMediumType, String messageType, String language) {
//        receiveMedium = receiveMediumFactory(receiveMediumType);
//        List<Product> purchasedProducts = order.getProducts();
//        if (receiveMedium.send(purchasedProducts, order.getCustomerName(),messageType, language)) {
//            return true;
//        }
//        return false;
//    }
//
//    private ReceiveMedium receiveMediumFactory(String receiveMedium) {
//        ReceiveMedium medium = null;
//        if (receiveMedium.equals("sms")) {
//            medium = new SMS();
//        } else if (receiveMedium.equals("email")) {
//            medium = new Email();
//        }
//        return medium;
//    }
//
//}

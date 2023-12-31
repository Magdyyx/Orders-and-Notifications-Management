package com.example.FCAI.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FCAI.api.Enums.OrderState;
import com.example.FCAI.api.Repositories.OrderRepo;
import com.example.FCAI.api.model.Order.CompositeOrder;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;

@Service
public class OrderService {
    private OrderRepo orderRepo;
    private CustomerService customerService;
    private ProductService productService;
    private NotificationService notificationService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, ProductService productService, NotificationService notificationService){
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.productService = productService;
        this.notificationService = notificationService;
    }

    public Order update(Order oldOrder, Order o) {
        return orderRepo.update(oldOrder, o);
    }

    public boolean delete(int id) {
        Order o = orderRepo.findById(id);
        if (o == null)
            return false;

        orderRepo.delete(o);
        return true;
    }

    public Order findById(int id) {
        return orderRepo.findById(id);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public SimpleOrder placeSimpleOrder(Customer loggedInCustomer, Map<Integer, Integer> requestedProducts) {
        SimpleOrder order = placeSimpleOrder(loggedInCustomer, requestedProducts, Order.getShippingFeeCost());

        // Invoke a method that changes the order state
        // To prevent cancelling order after 30 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            shipOrder(order);
        }, 30, TimeUnit.SECONDS);
        if(order != null)
            notificationService.sendNotification(order, "English", "Email", "order");
        return order;
    }

    private SimpleOrder placeSimpleOrder(Customer customer, Map<Integer, Integer> requestedProducts,
            double shippingFee) {
        // Check District and Address
        if (customer.getDistrict() == null || customer.getAddress() == null)
            return null;
        List<Product> products = productService.getProducts(requestedProducts);
        if (products.size() != requestedProducts.size())
            return null;

        double totalPrice = 0;
        var iterator = requestedProducts.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            int quantity = iterator.next().getValue();
            if (quantity > products.get(i).getRemainingQuantity()) {
                return null;
            }
            totalPrice += products.get(i).getPrice() * quantity;
            i++;
        }

        if (customer.getBalance() < totalPrice + shippingFee)
            return null;

        customer.setBalance((customer.getBalance() - (totalPrice + shippingFee)));

        customerService.updateCustomer(customer.getId(), customer);
        iterator = requestedProducts.entrySet().iterator();
        i = 0;
        while (iterator.hasNext()) {
            productService.reduceQuantity(products.get(i).getSerialNumber(), iterator.next().getValue());
            i++;
        }
        SimpleOrder order = new SimpleOrder(totalPrice, shippingFee, customer.getDistrict(), customer.getAddress(),
                customer.getId(), true, OrderState.Placed, requestedProducts);
        orderRepo.create(order);

        return order;
    }

    public CompositeOrder placeCompoundOrder(Customer loggedInCustomer,
            Map<Integer, Map<Integer, Integer>> customersAndProducts) {

        double totalPrice = 0;
        if (loggedInCustomer.getAddress() == null || loggedInCustomer.getDistrict() == null)
            return null;
        // Check for Quantity
        Map<Integer, Integer> totalProducts = new HashMap<>();

        for (var customer : customersAndProducts.entrySet()) {
            for (var product : customer.getValue().entrySet()) {
                if (totalProducts.containsKey(product.getKey())) {
                    totalProducts.put(product.getKey(), totalProducts.get(product.getKey()) + product.getValue());
                } else {
                    totalProducts.put(product.getKey(), product.getValue());
                }
            }
        }
        List<Product> products = productService.getProducts(totalProducts);
        if (products.size() != totalProducts.size())
            return null;

        for (var product : totalProducts.entrySet()) {
            if (product.getValue() > productService.getProduct(product.getKey()).getRemainingQuantity()) {
                return null;
            }
        }
        // Check for Balance
        for (var entry : customersAndProducts.entrySet()) {
            int customerId = entry.getKey();
            Map<Integer, Integer> productsMap = entry.getValue();

            // Step 1: Check balance for each customer and their corresponding order
            Customer customer = customerService.getCustomer(customerId);
            if (customer == null || customer.getBalance() < (calculateOrderTotalPrice(productsMap)
                    + (Order.getShippingFeeCost() / customersAndProducts.size()))) {
                return null;
            }
        }
        // Check for same district for Customers
        for (var entry : customersAndProducts.entrySet()) {
            int customerId = entry.getKey();
            // Step 1: Check District for each customer and their corresponding order
            Customer customer = customerService.getCustomer(customerId);
            if (customer == null || !customer.getDistrict().equals(loggedInCustomer.getDistrict())) {
                return null;
            }
        }

        List<Order> confirmedOrders = new ArrayList<>();

        for (var entry : customersAndProducts.entrySet()) {
            int customerId = entry.getKey();
            Map<Integer, Integer> productsMap = entry.getValue();
            Customer customer = customerService.getCustomer(customerId);

            SimpleOrder simpleOrder = placeSimpleOrder(customer, productsMap,
                    Order.getShippingFeeCost() / customersAndProducts.size());
            simpleOrder.setCanCancelOrder(false);

            confirmedOrders.add(simpleOrder);
        }

        for (Order order : confirmedOrders) {
            totalPrice += order.getTotalPrice();
        }

        // Create Composoite Order
        CompositeOrder compositeOrder = new CompositeOrder(totalPrice, Order.getShippingFeeCost(),
                loggedInCustomer.getDistrict(), loggedInCustomer.getAddress(),
                loggedInCustomer.getId(), true, OrderState.Placed, confirmedOrders);
        orderRepo.create(compositeOrder);

        // Invoke a method that changes the order state
        // To prevent cancelling order after 30 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            shipOrder(compositeOrder);
        }, 30, TimeUnit.SECONDS);
        if(compositeOrder != null)
            notificationService.sendNotification(compositeOrder, "English", "Email", "order");
        return compositeOrder;
    }

    // Helper method to calculate the total price of an order
    private double calculateOrderTotalPrice(Map<Integer, Integer> productsMap) {
        double orderTotalPrice = 0;

        for (var productEntry : productsMap.entrySet()) {
            int productId = productEntry.getKey();
            int quantity = productEntry.getValue();
            Product product = productService.getProduct(productId);
            orderTotalPrice += product.getPrice() * quantity;
        }

        return orderTotalPrice + Order.getShippingFeeCost();
    }

    public Object CancelOrder(Customer loggedInCustomer, int order) {
        Order orderToCancel = orderRepo.findById(order);
        if (orderToCancel == null) {
            return null;
        }

        if (!orderToCancel.CanCancelOrder() || !(orderToCancel.getState() == OrderState.Placed)) {
            return null;
        }

        if (orderToCancel.getCustomerID() != loggedInCustomer.getId()) {
            return null;
        }

        if (orderToCancel instanceof SimpleOrder) {
            SimpleOrder simpleOrder = (SimpleOrder) orderToCancel;
            Map<Integer, Integer> products = simpleOrder.getProducts();
            for (var product : products.entrySet()) {
                productService.increaseQuantity(product.getKey(), product.getValue());
            }
            Customer customer = customerService.getCustomer(simpleOrder.getCustomerID());
            customer.setBalance(customer.getBalance() + simpleOrder.getTotalPrice() + simpleOrder.getShippingFee());
            customerService.updateCustomer(customer.getId(), customer);
            orderRepo.delete(orderToCancel);
            return simpleOrder;
        } else {
            CompositeOrder compositeOrder = (CompositeOrder) orderToCancel;
            List<Order> orders = compositeOrder.getOrders();
            for (var order1 : orders) {
                SimpleOrder simpleOrder = (SimpleOrder) order1;
                Map<Integer, Integer> products = simpleOrder.getProducts();
                for (var product : products.entrySet()) {
                    productService.increaseQuantity(product.getKey(), product.getValue());
                }
                Customer customer = customerService.getCustomer(simpleOrder.getCustomerID());
                customer.setBalance(customer.getBalance() + simpleOrder.getTotalPrice() + simpleOrder.getShippingFee());
                customerService.updateCustomer(customer.getId(), customer);
                orderRepo.delete(order1);
            }
            orderRepo.delete(orderToCancel);
            return compositeOrder;
        }
    }

    private void shipOrder(Order order) {
        order.setState(OrderState.Shipping);
        order.setCanCancelOrder(false);
    }
}

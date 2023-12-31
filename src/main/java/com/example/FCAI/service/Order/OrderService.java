package com.example.FCAI.service.Order;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.NotificationService;
import com.example.FCAI.service.Order.CancelOrder.CancelOrderValidator;
import com.example.FCAI.service.Order.CancelOrder.CompositeOrderCancellationStrategy;
import com.example.FCAI.service.Order.CancelOrder.SimpleOrderCancellationStrategy;
import com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation.*;
import com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation.*;
import com.example.FCAI.service.ProductService;
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
    private SimpleOrderValidator simpleOrderValidator;
    private CompositeOrderValidator compositeOrderValidator;

    private CancelOrderValidator cancelOrderValidator;
    List<SimpleValidationStrategy> simpleValidationStrategies;
    List<CompositeOrderValidationStrategy> compositeValidationStrategies;




    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, ProductService productService, NotificationService notificationService){
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.productService = productService;
        this.notificationService = notificationService;
        simpleValidationStrategies = Arrays.asList(
                new DistrictAndAddressSimpleValidation(),
                new ProductsSimpleValidation(productService),
                new RemainingQuantitySimpleValidation(productService),
                new BalanceSimpleValidation(productService)
        );
        compositeValidationStrategies = Arrays.asList(
                new DistrictValidationStrategy(customerService),
                new QuantityValidationStrategy(productService),
                new BalanceValidationStrategy(customerService, productService)
        );

        cancelOrderValidator = new CancelOrderValidator(Arrays.asList(
                new SimpleOrderCancellationStrategy(productService, customerService, orderRepo),
                new CompositeOrderCancellationStrategy(productService, customerService, orderRepo)
        ));
        compositeOrderValidator = new CompositeOrderValidator(compositeValidationStrategies);
        simpleOrderValidator = new SimpleOrderValidator(simpleValidationStrategies);
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
        //Validate Order
        if(!simpleOrderValidator.validateOrder(customer, requestedProducts, shippingFee))
            return null;
        //Update Customer Balance
        double totalPrice = calculateOrderTotalPrice(requestedProducts);
        System.out.println("Total Price: " + totalPrice);
        customer.setBalance((customer.getBalance() - (totalPrice + shippingFee)));
        customerService.updateCustomer(customer.getId(), customer);
        SimpleOrder order = new SimpleOrder(totalPrice, shippingFee, customer.getDistrict(), customer.getAddress(),
                customer.getId(), true, OrderState.Placed, requestedProducts);
        orderRepo.create(order);

        return order;
    }

    public CompositeOrder placeCompoundOrder(Customer loggedInCustomer,
            Map<Integer, Map<Integer, Integer>> customersAndProducts) {

        //Validate Order
        if(!compositeOrderValidator.validateCompositeOrder(loggedInCustomer, customersAndProducts))
            return null;

        double totalPrice = 0;
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
            notificationService.sendNotification(compositeOrder, "Arabic", "SMS", "shipment");
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

        return orderTotalPrice;
    }

//    public Object CancelOrder(Customer loggedInCustomer, int order) {
//        Order orderToCancel = orderRepo.findById(order);
//        if (orderToCancel == null) {
//            return null;
//        }
//
//        if (!orderToCancel.CanCancelOrder() || !(orderToCancel.getState() == OrderState.Placed)) {
//            return null;
//        }
//
//        if (orderToCancel.getCustomerID() != loggedInCustomer.getId()) {
//            return null;
//        }
//
//        if (orderToCancel instanceof SimpleOrder) {
//            SimpleOrder simpleOrder = (SimpleOrder) orderToCancel;
//            Map<Integer, Integer> products = simpleOrder.getProducts();
//            for (var product : products.entrySet()) {
//                productService.increaseQuantity(product.getKey(), product.getValue());
//            }
//            Customer customer = customerService.getCustomer(simpleOrder.getCustomerID());
//            customer.setBalance(customer.getBalance() + simpleOrder.getTotalPrice() + simpleOrder.getShippingFee());
//            customerService.updateCustomer(customer.getId(), customer);
//            orderRepo.delete(orderToCancel);
//            return simpleOrder;
//        } else {
//            CompositeOrder compositeOrder = (CompositeOrder) orderToCancel;
//            List<Order> orders = compositeOrder.getOrders();
//            for (var order1 : orders) {
//                SimpleOrder simpleOrder = (SimpleOrder) order1;
//                Map<Integer, Integer> products = simpleOrder.getProducts();
//                for (var product : products.entrySet()) {
//                    productService.increaseQuantity(product.getKey(), product.getValue());
//                }
//                Customer customer = customerService.getCustomer(simpleOrder.getCustomerID());
//                customer.setBalance(customer.getBalance() + simpleOrder.getTotalPrice() + simpleOrder.getShippingFee());
//                customerService.updateCustomer(customer.getId(), customer);
//                orderRepo.delete(order1);
//            }
//            orderRepo.delete(orderToCancel);
//            return compositeOrder;
//        }
//    }

    public Object CancelOrder(Customer loggedInCustomer, int orderId) {
        Order orderToCancel = orderRepo.findById(orderId);
        if (orderToCancel == null) {
            // Order not found
            return null;
        }

        // Use the CancelOrderValidator to perform the cancellation
        return cancelOrderValidator.cancelOrder(orderToCancel, loggedInCustomer);
    }


    private void shipOrder(Order order) {
        order.setState(OrderState.Shipping);
        order.setCanCancelOrder(false);
    }

    public Order getOrderById(int id) {
        return orderRepo.findById(id);
    }
}

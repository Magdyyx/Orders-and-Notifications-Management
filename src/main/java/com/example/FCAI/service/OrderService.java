package com.example.FCAI.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.api.model.RequestedProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FCAI.api.Repositories.OrderRepo;
import com.example.FCAI.api.model.Order.CompositeOrder;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;

@Service
public class OrderService {
    private OrderRepo orderRepo;
    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.productService = productService;
    }

    private boolean checkOrderData(Order o) {
        if (o == null)
            return false;

        if (orderRepo.findById(o.getId()) != null)
            return false;

        if (customerService.getCustomer(o.getCustomerID()) == null)
            return false;

        return true;
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

        if (loggedInCustomer.getBalance() < totalPrice + Order.getShippingFeeCost())
            return null;

        loggedInCustomer.setBalance((loggedInCustomer.getBalance() - (totalPrice + Order.getShippingFeeCost())));

        customerService.updateCustomer(loggedInCustomer.getId(), loggedInCustomer);

        i = 0;
        while (iterator.hasNext()) {
            productService.reduceQuantity(products.get(i).getSerialNumber(), iterator.next().getValue());
            i++;
        }
        SimpleOrder order = new SimpleOrder(totalPrice, Order.getShippingFeeCost(), "El-Dokki", "Haram",
                loggedInCustomer.getId(), requestedProducts);
        orderRepo.create(order);

        return order;
    }

    // public boolean canPlaceSimpleOrder(SimpleOrder order) {

    // }

    // public Order placeCompoundOrder(Customer loggedInCustomer, List<SimpleOrder> orders) {
    //     //Implement this method
    //     //Products
    //     Map<Integer, Integer> totalProducts = new HashMap<>();


    //     for (SimpleOrder order : orders) {
    //         for (var product : order.getProducts().entrySet()) {
    //             if (totalProducts.containsKey(product.getKey())) {
    //                 totalProducts.put(product.getKey(), totalProducts.get(product.getKey()) + product.getValue());
    //             } else {
    //                 totalProducts.put(product.getKey(), product.getValue());
    //             }
    //         }
    //     }

    //     //Customers
    //     double totalPrice = 0;

    //     for (SimpleOrder order : orders) {
    //         canPlaceSimpleOrder(order)
    //         SimpleOrder simpleOrder = new SimpleOrder(order);
    //         totalPrice += order.getTotalPrice();
    //     }

    //     if (loggedInCustomer.getBalance() < totalPrice)
    //         return null;

    //     loggedInCustomer.setBalance((loggedInCustomer.getBalance() - totalPrice));
    //     customerService.updateCustomer(loggedInCustomer.getId(), loggedInCustomer);

    //     CompositeOrder order = new CompositeOrder(totalPrice, 20, "Giza", "Haram", loggedInCustomer.getId(), orders);
    //     orderRepo.create(order);

    //     return order;
    // }
}

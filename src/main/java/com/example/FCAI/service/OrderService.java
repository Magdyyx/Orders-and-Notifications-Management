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

//    public boolean createSimpleOrder(Order order) {
//        if (!checkOrder((SimpleOrder) order, order.getDeliveryDistrict(), order.getShippingFee()))
//            return false;
//
//        orderRepo.create(order);
//        return true;
//    }
//
//    public boolean createComplexOrder(Order o) {
//        if(!checkOrder((CompositeOrder) o, o.getDeliveryDistrict(), o.getShippingFee()))
//            return false;
//
//        // Create Order
//        return true;
//    }

//    private boolean checkOrder(Order o, String district, double shippingFee) {
//        if (district == null || o.getDeliveryDistrict() != district)
//            return false;
//
//        if (!checkOrderData(o))
//            return false;
//
//        if (o instanceof SimpleOrder)
//            return canCreateSimpleOrder((SimpleOrder) o);
//
//        return canCreateComplexOrder((CompositeOrder) o);
//    }

//    private boolean canCreateSimpleOrder(SimpleOrder o) {
//        String district = o.getDeliveryDistrict();
//        if (district == null || district.isBlank() || district.isEmpty())
//            return false;
//
//        for (var product : o.getProducts().entrySet()) {
//            if (productService.getProduct(product.getKey()) == null)
//                return false;
//        }
//
//        return true;
//    }

//    private boolean canCreateComplexOrder(CompositeOrder o) {
//        for (Order order : o.getOrders()) {
//            if (!checkOrder(order, o.getDeliveryDistrict(), o.getShippingFee()))
//                return false;
//        }
//
//        return true;
//    }

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

    public SimpleOrder placeSimpleOrder(Customer loggedInCustomer, List<RequestedProducts> requestedProducts) {
        List<Product> products = productService.getProducts(requestedProducts);
        if (products.size() != requestedProducts.size())
            return null;
//        System.out.println("test 1 passed " + products.size() + " " + requestedProducts.size());

        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            if (requestedProducts.get(i).getQuantity() > products.get(i).getRemainingQuantity()) {
                return null;
            }
            totalPrice += products.get(i).getPrice() * requestedProducts.get(i).getQuantity();
        }
//        System.out.println("test 2 passed " + totalPrice);

        if (loggedInCustomer.getBalance() < totalPrice)
            return null;

//        System.out.println("test 3 passed " + loggedInCustomer.getBalance());


        loggedInCustomer.setBalance((loggedInCustomer.getBalance() - totalPrice));
//        System.out.println("test 4 passed: new loginCustomer balance " + loggedInCustomer.getBalance());

        customerService.updateCustomer(loggedInCustomer.getId(), loggedInCustomer);

//        System.out.println("test 5 passed: customer updated balance " + customerService.getCustomer(loggedInCustomer.getId()).getBalance());

        for (int i = 0; i < requestedProducts.size(); i++){
            productService.reduceQuantity(products.get(i).getSerialNumber(), requestedProducts.get(i).getQuantity());
//            System.out.println("test 6 passed: products remaining quantity updated " + productService.getProduct(products.get(i).getSerialNumber()).getRemainingQuantity());
        }
        SimpleOrder order = new SimpleOrder(totalPrice, 20, "Giza", "Haram",loggedInCustomer.getId(), products);
        orderRepo.create(order);
//        System.out.println("test 7 passed: new order placed " + orderRepo.findById(1).getId());

//        if (order == null) {
//            System.out.println("Test 8 failed: order is null");
//        } else {
//            System.out.println("Test 8 passed: order is not null");
//        }

        return order;
    }


    public boolean canPlaceSimpleOrder(SimpleOrder order) {


    }


    public Order placeCompoundOrder(Customer loggedInCustomer, List<SimpleOrder> orders) {
        //Implement this method
        //Products
        Map<Integer, Integer> totalProducts = new HashMap<>();


        for (SimpleOrder order : orders) {
            for (var product : order.getProducts().entrySet()) {
                if (totalProducts.containsKey(product.getKey())) {
                    totalProducts.put(product.getKey(), totalProducts.get(product.getKey()) + product.getValue());
                } else {
                    totalProducts.put(product.getKey(), product.getValue());
                }
            }
        }

        //Customers
        double totalPrice = 0;

        for (SimpleOrder order : orders) {
            canPlaceSimpleOrder(order)
            SimpleOrder simpleOrder = new SimpleOrder(order);
            totalPrice += order.getTotalPrice();
        }

        if (loggedInCustomer.getBalance() < totalPrice)
            return null;

        loggedInCustomer.setBalance((loggedInCustomer.getBalance() - totalPrice));
        customerService.updateCustomer(loggedInCustomer.getId(), loggedInCustomer);

        CompositeOrder order = new CompositeOrder(totalPrice, 20, "Giza", "Haram", loggedInCustomer.getId(), orders);
        orderRepo.create(order);

        return order;
    }
}

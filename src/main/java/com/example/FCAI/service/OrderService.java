package com.example.FCAI.service;

import java.util.List;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
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

    public boolean createSimpleOrder(Order o) {
        if (!checkOrder((SimpleOrder) o, o.getDeliveryDistrict(), o.getShippingFee()))
            return false;

        orderRepo.create(o);
        return true;
    }

    public boolean createComplexOrder(Order o) {
        if(!checkOrder((CompositeOrder) o, o.getDeliveryDistrict(), o.getShippingFee()))
            return false;

        // Create Order
        return true;
    }

    private boolean checkOrder(Order o, String district, double shippingFee) {
        if (district == null || o.getDeliveryDistrict() != district)
            return false;

        if (!checkOrderData(o))
            return false;

        if (o instanceof SimpleOrder)
            return canCreateSimpleOrder((SimpleOrder) o);

        return canCreateComplexOrder((CompositeOrder) o);
    }

    private boolean canCreateSimpleOrder(SimpleOrder o) {
        String district = o.getDeliveryDistrict();
        if (district == null || district.isBlank() || district.isEmpty())
            return false;

        for (var product : o.getProducts().entrySet()) {
            if (productService.getProduct(product.getKey()) == null)
                return false;
        }

        return true;
    }

    private boolean canCreateComplexOrder(CompositeOrder o) {
        for (Order order : o.getOrders()) {
            if (!checkOrder(order, o.getDeliveryDistrict(), o.getShippingFee()))
                return false;
        }

        return true;
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

    public SimpleOrder placeSimpleOrder(Customer loggedInCustomer, List<Integer> serialNumbers) {
        List<Product> products = productService.getProducts(serialNumbers);
        if (products == null || products.size() != serialNumbers.size())
            return null;
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        if (loggedInCustomer.getBalance() < totalPrice)
            return null;

        loggedInCustomer.setBalance((int) (loggedInCustomer.getBalance() - totalPrice));
        customerService.updateCustomer(loggedInCustomer.getId(), loggedInCustomer);
        SimpleOrder order = new SimpleOrder(1, totalPrice, 30, "Giza", "ay7aga",loggedInCustomer.getId(), products);
        orderRepo.create(order);
        return order;
    }
}

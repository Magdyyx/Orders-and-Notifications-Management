package com.example.FCAI.service.Order.CancelOrder;

import com.example.FCAI.api.Enums.OrderState;
import com.example.FCAI.api.Repositories.OrderRepo;
import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Order.CompositeOrder;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.ProductService;

import java.util.List;
import java.util.Map;

public class CompositeOrderCancellationStrategy implements CancellationStrategy {
    private ProductService productService;
    private CustomerService customerService;
    private OrderRepo orderRepo;

    public CompositeOrderCancellationStrategy(ProductService productService, CustomerService customerService, OrderRepo orderRepo) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderRepo = orderRepo;
    }

    @Override
    public Object cancelOrder(Order order, Customer loggedInCustomer) {
        // Check if the order is a CompositeOrder
        if (!(order instanceof CompositeOrder))
            return null;

        CompositeOrder compositeOrder = (CompositeOrder) order;

        // Check if the order can be canceled and is in the correct state
        if (!(compositeOrder.CanCancelOrder() && compositeOrder.getState() == OrderState.Placed))
            return null;

        // Check if the logged-in customer is the owner of the order
        if (compositeOrder.getCustomerID() != loggedInCustomer.getId())
            return null;

        // Process cancellation for a CompositeOrder
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
        orderRepo.delete(order);
        return compositeOrder;
    }
}





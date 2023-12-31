package com.example.FCAI.service.Order.CancelOrder;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Order.Order;

public interface CancellationStrategy {
    Object cancelOrder(Order order, Customer loggedInCustomer);
}
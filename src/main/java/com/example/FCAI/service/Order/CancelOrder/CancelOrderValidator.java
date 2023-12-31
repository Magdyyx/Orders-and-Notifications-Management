package com.example.FCAI.service.Order.CancelOrder;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Order.Order;

import java.util.List;

public class CancelOrderValidator {
    private List<CancellationStrategy> cancellationStrategies;

    public CancelOrderValidator(List<CancellationStrategy> cancellationStrategies) {
        this.cancellationStrategies = cancellationStrategies;
    }

    public Object cancelOrder(Order order, Customer loggedInCustomer) {
        for (CancellationStrategy cancellationStrategy : cancellationStrategies) {
            // Execute the cancellation strategy and return the result if successful
            Object result = cancellationStrategy.cancelOrder(order, loggedInCustomer);
            if (result != null) {
                return result;
            }
        }
        // No strategy successfully executed
        return null;
    }
}

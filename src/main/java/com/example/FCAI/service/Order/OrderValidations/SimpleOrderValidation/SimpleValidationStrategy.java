package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;


import com.example.FCAI.api.model.Customer.Customer;

import java.util.Map;

public interface SimpleValidationStrategy {
    boolean validate(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee);
}
package com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;

import java.util.Map;

public interface CompositeOrderValidationStrategy {
    boolean validate(Customer loggedInCustomer, Map<Integer, Map<Integer, Integer>> customersAndProducts);
}

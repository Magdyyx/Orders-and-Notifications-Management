package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;

import java.util.List;
import java.util.Map;

public class SimpleOrderValidator {
    private List<SimpleValidationStrategy> validationStrategies;

    public SimpleOrderValidator(List<SimpleValidationStrategy> validationStrategies) {
        this.validationStrategies = validationStrategies;
    }

    public boolean validateOrder(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee) {
        for (SimpleValidationStrategy simpleValidationStrategy : validationStrategies) {
            if (!simpleValidationStrategy.validate(customer, requestedProducts, shippingFee)) {
                return false;
            }
        }
        return true;
    }
}
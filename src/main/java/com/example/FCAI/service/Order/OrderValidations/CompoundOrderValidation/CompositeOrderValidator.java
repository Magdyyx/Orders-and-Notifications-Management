package com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;

import java.util.List;
import java.util.Map;

public class CompositeOrderValidator {
    private List<CompositeOrderValidationStrategy> validationStrategies;

    public CompositeOrderValidator(List<CompositeOrderValidationStrategy> validationStrategies) {
        this.validationStrategies = validationStrategies;
    }

    public boolean validateCompositeOrder(Customer loggedInCustomer, Map<Integer, Map<Integer, Integer>> customersAndProducts) {
        for (CompositeOrderValidationStrategy validationStrategy : validationStrategies) {
            if (!validationStrategy.validate(loggedInCustomer, customersAndProducts)) {
                return false;
            }
        }
        return true;
    }
}
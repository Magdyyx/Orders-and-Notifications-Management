package com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.service.CustomerService;

import java.util.Map;

public class DistrictValidationStrategy implements CompositeOrderValidationStrategy {
    private CustomerService customerService;

    public DistrictValidationStrategy(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean validate(Customer loggedInCustomer, Map<Integer, Map<Integer, Integer>> customersAndProducts) {
        if (loggedInCustomer == null || loggedInCustomer.getDistrict() == null) {
            return false;
        }
        for (var entry : customersAndProducts.entrySet()) {
            int customerId = entry.getKey();
            Customer customer = customerService.getCustomer(customerId);
            if (customer == null || !customer.getDistrict().equals(loggedInCustomer.getDistrict())) {
                return false;
            }
        }
        return true;
    }
}
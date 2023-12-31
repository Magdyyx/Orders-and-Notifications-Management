package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;

import java.util.Map;

public class DistrictAndAddressSimpleValidation implements SimpleValidationStrategy {
    @Override
    public boolean validate(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee) {
        return customer.getDistrict() != null && customer.getAddress() != null;
    }
}
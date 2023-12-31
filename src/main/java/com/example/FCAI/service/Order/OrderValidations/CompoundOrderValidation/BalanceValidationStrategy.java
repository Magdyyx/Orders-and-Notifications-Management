package com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.ProductService;

import java.util.Map;

public class BalanceValidationStrategy implements CompositeOrderValidationStrategy {
    private CustomerService customerService;
    private ProductService productService;
    public BalanceValidationStrategy(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public boolean validate(Customer loggedInCustomer, Map<Integer, Map<Integer, Integer>> customersAndProducts) {
        for (var entry : customersAndProducts.entrySet()) {
            int customerId = entry.getKey();
            Map<Integer, Integer> productsMap = entry.getValue();
            Customer customer = customerService.getCustomer(customerId);
            if (customer == null ||
                    customer.getBalance() < calculateOrderTotalPrice(productsMap) + (Order.getShippingFeeCost() / customersAndProducts.size())) {
                return false;
            }
        }
        return true;
    }

    private double calculateOrderTotalPrice(Map<Integer, Integer> productsMap) {
        double orderTotalPrice = 0;

        for (var productEntry : productsMap.entrySet()) {
            int productId = productEntry.getKey();
            int quantity = productEntry.getValue();
            Product product = productService.getProduct(productId);
            orderTotalPrice += product.getPrice() * quantity;
        }

        return orderTotalPrice;
    }
}
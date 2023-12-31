package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.ProductService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

// BalanceValidation.java
public class BalanceSimpleValidation implements SimpleValidationStrategy {

    private ProductService productService;

    public BalanceSimpleValidation(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public boolean validate(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee) {
        double totalPrice = calculateTotalPrice(requestedProducts);
        return customer.getBalance() >= totalPrice + shippingFee;
    }

    private double calculateTotalPrice(Map<Integer, Integer> requestedProducts) {
        List<Product> products = productService.getProducts(requestedProducts);
        double totalPrice = 0;
        Iterator<Map.Entry<Integer, Integer>> iterator = requestedProducts.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            int quantity = iterator.next().getValue();
            totalPrice += products.get(i).getPrice() * quantity;
            i++;
        }
        return totalPrice;
    }
}
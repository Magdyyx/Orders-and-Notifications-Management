package com.example.FCAI.service.Order.OrderValidations.CompoundOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuantityValidationStrategy implements CompositeOrderValidationStrategy {
    private ProductService productService;

    public QuantityValidationStrategy(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean validate(Customer loggedInCustomer, Map<Integer, Map<Integer, Integer>> customersAndProducts) {
        Map<Integer, Integer> totalProducts = calculateTotalProducts(customersAndProducts);
        List<Product> products = productService.getProducts(totalProducts);
        return products.size() == totalProducts.size() && validateProductQuantities(products, totalProducts);
    }

    private Map<Integer, Integer> calculateTotalProducts(Map<Integer, Map<Integer, Integer>> customersAndProducts) {
        Map<Integer, Integer> totalProducts = new HashMap<>();
        for (var customer : customersAndProducts.entrySet()) {
            for (var product : customer.getValue().entrySet()) {
                totalProducts.merge(product.getKey(), product.getValue(), Integer::sum);
            }
        }
        return totalProducts;
    }

    private boolean validateProductQuantities(List<Product> products, Map<Integer, Integer> totalProducts) {
        for (var product : totalProducts.entrySet()) {
            if (product.getValue() > productService.getProduct(product.getKey()).getRemainingQuantity()) {
                return false;
            }
        }
        return true;
    }
}
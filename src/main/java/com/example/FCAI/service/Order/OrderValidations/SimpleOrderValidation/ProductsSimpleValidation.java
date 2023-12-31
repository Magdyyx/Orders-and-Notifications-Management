package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.ProductService;

import java.util.List;
import java.util.Map;

public class ProductsSimpleValidation implements SimpleValidationStrategy {
    private ProductService productService;

    public ProductsSimpleValidation(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean validate(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee) {
        List<Product> products = productService.getProducts(requestedProducts);
        return products.size() == requestedProducts.size();
    }
}
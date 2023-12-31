package com.example.FCAI.service.Order.OrderValidations.SimpleOrderValidation;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.ProductService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

// RemainingQuantityValidation.java
public class RemainingQuantitySimpleValidation implements SimpleValidationStrategy {

    private ProductService productService;

    public RemainingQuantitySimpleValidation(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public boolean validate(Customer customer, Map<Integer, Integer> requestedProducts, double shippingFee) {
        List<Product> products = productService.getProducts(requestedProducts);
        Iterator<Map.Entry<Integer, Integer>> iterator = requestedProducts.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            int quantity = iterator.next().getValue();
            if (quantity > products.get(i).getRemainingQuantity()) {
                return false;
            }
            i++;
        }
        return true;
    }
}
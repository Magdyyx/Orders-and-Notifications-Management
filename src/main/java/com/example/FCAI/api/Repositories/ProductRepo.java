package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.RepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepo implements RepositoryService <Product> {
    private List<Product> products;


    public ProductRepo() {
        products = new ArrayList<>();
        Product product1 = new Product("mega", 1, "nestle", "ice cream", 20, 20);
        Product product2 = new Product("hohos", 2, "vendor2", "cake", 3, 60);
        Product product3 = new Product("big chips", 3, "egypt foods", "chipsy", 5, 40);
        products.addAll(List.of(product1, product2, product3));

    }
    @Override
    public Product create(Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == product.getSerialNumber()) {

                return null;
            }
        }
        Product newProduct = new Product(product.getName(), product.getSerialNumber(),
                product.getVendor(), product.getCategory(), product.getPrice(), product.getRemainingQuantity());
        products.add(newProduct);
        return newProduct;
    }

    @Override
    public Product update(Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == product.getSerialNumber()) {
                currentProduct.setName(product.getName());
                currentProduct.setVendor(product.getVendor());
                currentProduct.setCategory(product.getCategory());
                currentProduct.setPrice(product.getPrice());
                currentProduct.setPrice(currentProduct.getRemainingQuantity());

                return currentProduct;
            }
        }
        return null;
    }

    @Override
    public Product delete(Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == product.getSerialNumber()) {
                products.remove(currentProduct);
                return currentProduct;
            }
        }

        return null;
    }

    @Override
    public Product findById(int serialNumber) {
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == serialNumber) {
                return currentProduct;
            }
        }

        return null;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }
}

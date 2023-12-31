package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.RepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepo implements RepositoryService<Product> {
    private List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
//        Product product1 = new Product("mega", 1, "nestle", "ice cream", 20, 20);
//        Product product2 = new Product("hohos", 2, "vendor2", "cake", 3, 60);
//        Product product3 = new Product("big chips", 3, "egypt foods", "chipsy", 5, 40);
//        products.addAll(List.of(product1, product2, product3));

    }

    @Override
    public Product create(Product product) {
        Product newProduct = new Product(product);
        products.add(product);

        return newProduct;
    }

    @Override
    public Product update(Product existingProduct, Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == existingProduct.getSerialNumber()) {
                Product updatedProduct = new Product(product);
                products.set(products.indexOf(currentProduct), product);
                return updatedProduct;
            }
        }

        return null;
    }

    @Override
    public Product delete(Product product) {
        products.remove(product);
        return product;
    }

    @Override
    public Product findById(int serialNumber) {
        Product foundProduct;
        for (Product currentProduct : products) {
            if (currentProduct.getSerialNumber() == serialNumber) {
                foundProduct = new Product(currentProduct);
                return foundProduct;
            }
        }

        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProducts = new ArrayList<>(products);
        return allProducts;
    }
}

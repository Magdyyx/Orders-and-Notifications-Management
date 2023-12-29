package com.example.FCAI.service;

import com.example.FCAI.api.Repositories.ProductRepo;
import com.example.FCAI.api.model.Customer;
import com.example.FCAI.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product getProduct(int serialNumber) {
        return productRepo.findById(serialNumber);
    }
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product createProduct(Product product) {
        return productRepo.create(product);
    }

    public Product updateProduct(int serialNumber, Product product) {
        return productRepo.update(product);
    }
    public Product updateProductName(int serialNumber, String newName) {
        Product product = productRepo.findById(serialNumber);
        product.setName(newName);
        return productRepo.update(product);
    }

    public Product updateProductVendor(int serialNumber, String newVendor) {
        Product product = productRepo.findById(serialNumber);
        product.setVendor(newVendor);
        return productRepo.update(product);
    }
    public Product updateProductCategory(int serialNumber, String category) {
        Product product = productRepo.findById(serialNumber);
        product.setCategory(category);
        return productRepo.update(product);
    }
    public Product updateProductPrice(int serialNumber, float newPrice) {
        Product product = productRepo.findById(serialNumber);
        product.setPrice(newPrice);
        return productRepo.update(product);
    }
    public Product updateProductRemainingQuantity(int serialNumber, int reductionQuantity) {
        Product product = productRepo.findById(serialNumber);
        product.setRemainingQuantity(product.getRemainingQuantity() - reductionQuantity);
        return productRepo.update(product);
    }


    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

}

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
        Product existingProduct = productRepo.findById(product.getSerialNumber());
        if (existingProduct != null) {
            return null;
        }
        return productRepo.create(product);
    }

    public Product updateProduct(int serialNumber, Product product) {
        Product existingProduct = productRepo.findById(serialNumber);
        return productRepo.update(existingProduct, product);
    }
    public Product updateProductName(int serialNumber, String newName) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        updatedProduct.setName(newName);
        return productRepo.update(existingProduct, updatedProduct);
    }

    public Product updateProductVendor(int serialNumber, String newVendor) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        existingProduct.setVendor(newVendor);
        return productRepo.update(existingProduct, updatedProduct);
    }
    public Product updateProductCategory(int serialNumber, String category) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        existingProduct.setCategory(category);
        return productRepo.update(existingProduct, updatedProduct);
    }
    public Product updateProductPrice(int serialNumber, float newPrice) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        existingProduct.setPrice(newPrice);
        return productRepo.update(existingProduct, updatedProduct);
    }
    public Product updateRemainingQuantity(int serialNumber, int newRemainingQuantity) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        existingProduct.setRemainingQuantity(newRemainingQuantity);
        return productRepo.update(existingProduct, updatedProduct);
    }

    public Product reduceQuantity(int serialNumber, int reductionQuantity) {
        Product existingProduct = productRepo.findById(serialNumber);
        Product updatedProduct = new Product(existingProduct);
        existingProduct.setRemainingQuantity(existingProduct.getRemainingQuantity() - reductionQuantity);
        return productRepo.update(existingProduct, updatedProduct);
    }


    public void deleteProduct(Product product) {
        deleteProduct(product.getSerialNumber());
    }

    public void deleteProduct(int serialNumber) {
        Product exisitingProduct = productRepo.findById(serialNumber);
        productRepo.delete(exisitingProduct);
    }

}


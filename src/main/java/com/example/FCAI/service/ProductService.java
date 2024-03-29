package com.example.FCAI.service;

import com.example.FCAI.api.Repositories.ProductRepo;
import com.example.FCAI.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
    public List<Product> getProducts(Map<Integer, Integer> requestedProducts) {
        List<Product> products = new ArrayList<>();
        var iterator = requestedProducts.entrySet().iterator();
        while (iterator.hasNext()) {
            Product product = productRepo.findById(iterator.next().getKey());
            if (product != null) {
                products.add(product);
            }
        }

        return products;
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
        if (existingProduct == null) {
            return null;
        }
        return productRepo.update(existingProduct, product);
    }

    public <T> Product updateProductAttribute(int serialNumber, T attributeValue, BiConsumer<Product, T> attributeUpdater) {
        Product existingProduct = productRepo.findById(serialNumber);
        if (existingProduct == null) {
            return null;
        }
        Product updatedProduct = new Product(existingProduct);
        attributeUpdater.accept(updatedProduct, attributeValue);
        return productRepo.update(existingProduct, updatedProduct);
    }
    public Product updateProductName(int serialNumber, String newName) {
        return updateProductAttribute(serialNumber, newName, Product::setName);
    }

    public Product updateProductVendor(int serialNumber, String newVendor) {
        return updateProductAttribute(serialNumber, newVendor, Product::setVendor);
    }

    public Product updateProductCategory(int serialNumber, String category) {
        return updateProductAttribute(serialNumber, category, Product::setCategory);
    }

    public Product updateProductPrice(int serialNumber, float newPrice) {
        return updateProductAttribute(serialNumber, newPrice, Product::setPrice);
    }

    public Product updateRemainingQuantity(int serialNumber, int newRemainingQuantity) {
        return updateProductAttribute(serialNumber, newRemainingQuantity, Product::setRemainingQuantity);
    }


    public Product reduceQuantity(int serialNumber, int reductionQuantity) {
        Product existingProduct = productRepo.findById(serialNumber);
        if (existingProduct == null) {
            return null;
        }
        Product updatedProduct = new Product(existingProduct);
        updatedProduct.setRemainingQuantity(existingProduct.getRemainingQuantity() - reductionQuantity);
        return updateProduct(existingProduct.getSerialNumber(), updatedProduct);
    }


    public void deleteProduct(Product product) {
        deleteProduct(product.getSerialNumber());
    }

    public Product deleteProduct(int serialNumber) {
        Product exisitingProduct = productRepo.findById(serialNumber);
        if (exisitingProduct != null) {
            return productRepo.delete(exisitingProduct);
        }
        return null;
    }

    public void increaseQuantity(Integer key, Integer value) {
        Product existingProduct = productRepo.findById(key);
        if (existingProduct != null) {
            Product updatedProduct = new Product(existingProduct);
            updatedProduct.setRemainingQuantity(existingProduct.getRemainingQuantity() + value);
            productRepo.update(existingProduct, updatedProduct);
        }
    }
}


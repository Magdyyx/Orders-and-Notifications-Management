package com.example.FCAI.api.controller;
import com.example.FCAI.api.model.Product;
import com.example.FCAI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<Product> getProductBySerialNumber(@PathVariable int serialNumber) {
        Product product = productService.getProduct(serialNumber);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        if (createdProduct != null) {
            return ResponseEntity.ok(createdProduct);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Product already exists");

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping ("/update/{serialNumber}")
    public ResponseEntity<Product> updateProduct(@PathVariable int serialNumber,@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(serialNumber, product);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/name/{serialNumber}")
    public ResponseEntity<Product> updateProductName(@PathVariable int serialNumber, @RequestBody String newName) {
        Product updatedProduct = productService.updateProductName(serialNumber, newName);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/vendor/{serialNumber}")
    public ResponseEntity<Product> updateProductVendor(@PathVariable int serialNumber, @RequestBody String vendor) {
        Product updatedProduct = productService.updateProductVendor(serialNumber, vendor);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/category/{serialNumber}")
    public ResponseEntity<Product> updateProductCategory(@PathVariable int serialNumber, @RequestBody String category) {
        Product updatedProduct = productService.updateProductCategory(serialNumber, category);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/price/{serialNumber}")
    public ResponseEntity<Product> updateProductCategory(@PathVariable int serialNumber, @RequestBody float price) {
        Product updatedProduct = productService.updateProductPrice(serialNumber, price);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/quantity/{serialNumber}")
    public ResponseEntity<Product> updateQuantity(@PathVariable int serialNumber, @RequestBody int newQuantity) {
        Product updatedProduct = productService.updateRemainingQuantity(serialNumber, newQuantity);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/quantity/reduce/{serialNumber}")
    public ResponseEntity<Product> reduceQuantity(@PathVariable int serialNumber, @RequestBody int reductionQuantity) {
        Product updatedProduct = productService.reduceQuantity(serialNumber, reductionQuantity);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping ("/delete/{serialNumber}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int serialNumber) {
        Product deletedProduct = productService.deleteProduct(serialNumber);
        if (deletedProduct != null) {
            return ResponseEntity.ok(deletedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

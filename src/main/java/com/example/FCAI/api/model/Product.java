package com.example.FCAI.api.model;

public class Product {
    String name;
    int serialNumber;
    String vendor;
    String category;
    float price;
    int remainingQuantity;

    public Product(String name, int serialNumber, String vendor, String category, float price, int quantity) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.category = category;
        this.price = price;
        this.remainingQuantity = quantity;
    }

    public Product(Product product) {
        this.name = product.getName();
        this.serialNumber = product.getSerialNumber();
        this.vendor = product.getVendor();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.remainingQuantity = product.remainingQuantity;
    }

    public String getName() {
        return name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}

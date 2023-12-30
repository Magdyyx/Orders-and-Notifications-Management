package com.example.FCAI.api.model.Customer;

public class Customer {
    private static int lastId = 0;
    int id;
    String name;
    double balance;
    String district;
    String address;

    public Customer(int id, String name, double balance, String District, String Address) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.district = District;
        this.address = Address;
    }

    public Customer(String name, double balance, String District, String Address) {
        this.id = lastId++;
        this.name = name;
        this.balance = balance;
        this.district = District;
        this.address = Address;
    }

    public Customer() {
    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.name = customer.name;
        this.balance = customer.balance;
        this.district = customer.district;
        this.address = customer.address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String District) {
        this.district = District;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }
}

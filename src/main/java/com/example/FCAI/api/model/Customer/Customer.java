package com.example.FCAI.api.model.Customer;


public class Customer {

    private static int lastId = 0;
    int id;
    String name;
    double balance;

    public Customer(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    public Customer(String name, double balance) {
        this.id = lastId++;
        this.name = name;
        this.balance = balance;
    }
    public Customer() {
    }


    public Customer(Customer customer) {
        this.id = customer.id;
        this.name = customer.name;
        this.balance = customer.balance;
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
        this.name= name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



}

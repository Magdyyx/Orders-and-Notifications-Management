package com.example.FCAI.api.model;

public class Customer {
    int id;
    String name;
    int balance;

    public Customer(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }



}

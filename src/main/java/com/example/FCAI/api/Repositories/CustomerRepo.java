package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Customer;
import com.example.FCAI.service.RepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerRepo implements RepositoryService <Customer>{
    private List<Customer> customers;


    public CustomerRepo() {
        customers = new ArrayList<>();
        Customer customer1 = new Customer(1, "Ahmed", 30);
        Customer customer2 = new Customer(2, "Mohamed", 20);
        Customer customer3 = new Customer(3, "Ali", 40);
        customers.addAll(List.of(customer1, customer2, customer3));
    }


    @Override
    public Customer create(Customer customer) {
        for (Customer customer1 : customers) {
            if (customer1.getId() == customer.getId()) {
                return null;
            }
        }
        Customer newCustomer = new Customer(customer.getId(), customer.getName(), customer.getBalance());
        customers.add(newCustomer);
        return newCustomer;
    }
    @Override
    public Customer update(Customer customer) {
        for (Customer customer1 : customers) {
            if (customer1.getId() == customer.getId()) {
                customer1.setName(customer.getName());
                customer1.setBalance(customer.getBalance());
                return customer1;
            }
        }
        return null;
    }

    @Override
    public Customer delete(Customer customer) {
        for (Customer customer1 : customers) {
            if (customer1.getId() == customer.getId()) {
                customers.remove(customer1);
                return customer1;
            }
        }
        return null;
    }

    @Override
    public Customer findById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}

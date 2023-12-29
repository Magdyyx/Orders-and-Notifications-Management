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
        customers.add(customer);
        return customer;
    }


    @Override
    public Customer update(Customer customer, Customer t1) {
        customer.setName(t1.getName());
        customer.setBalance(t1.getBalance());
        return customer;
    }

    @Override
    public Customer delete(Customer customer) {
        customers.remove(customer);
        return customer;
    }

    @Override
    public Customer findById(int id) {
        Customer foundCustomer;
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                foundCustomer = new Customer(customer);
                return foundCustomer;
            }
        }

        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}

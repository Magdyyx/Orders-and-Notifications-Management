package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.service.RepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerRepo implements RepositoryService<Customer> {
    private List<Customer> customers;

    public CustomerRepo() {
        customers = new ArrayList<>();
        Customer customer1 = new Customer("Ahmed", 1000, "Giza", "Haram");
        Customer customer2 = new Customer("Mohamed", 1000, "Giza", "Haram");
        Customer customer3 = new Customer("Ali", 1000, "Giza", "Haram");
        customers.addAll(List.of(customer1, customer2, customer3));
    }

    @Override
    public Customer create(Customer customer) {
        Customer newCustomer = new Customer(customer);
        customers.add(customer);
        return newCustomer;
    }

    @Override
    public Customer update(Customer customer, Customer t1) {
        for (Customer currentCustomer : customers) {
            if (currentCustomer.getId() == customer.getId()) {
                Customer updatedCustomer = new Customer(t1);
                customers.set(customers.indexOf(currentCustomer), t1);
                return updatedCustomer;
            }
        }

        return null;
    }

    @Override
    public Customer delete(Customer customer) {
        Customer deletedCustomer = new Customer(customer);
        customers.remove(customer);
        return deletedCustomer;
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
        List<Customer> allCustomers = new ArrayList<>(customers);
        return allCustomers;
    }
}

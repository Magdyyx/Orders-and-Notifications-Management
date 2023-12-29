package com.example.FCAI.service;

import com.example.FCAI.api.Repositories.CustomerRepo;
import com.example.FCAI.api.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }


    public Customer getCustomer(int id) {
        return customerRepo.findById(id);
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Customer existingCustomer = customerRepo.findById(customer.getId());
        if (existingCustomer != null) {
            return null;
        }
        Customer newCustomer = new Customer(customer.getId(), customer.getName(), customer.getBalance());
        return customerRepo.create(newCustomer);
    }


    public Customer updateCustomer(int id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepo.findById(id);
        if (existingCustomer == null) {
            return null;
        }
        return customerRepo.update(existingCustomer, updatedCustomer);
    }

    public Customer deleteCustomer(int id) {
        Customer existingCustomer = customerRepo.findById(id);
        if (existingCustomer == null) {
            return null;
        }
        return customerRepo.delete(existingCustomer);

    }
}

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
        return customerRepo.create(customer);
    }

    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepo.update(updatedCustomer);
    }

    public void deleteCustomer(Customer existingCustomer) {
        customerRepo.delete(existingCustomer);
    }
}

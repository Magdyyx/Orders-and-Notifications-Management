package com.example.FCAI.api.controller;
import com.example.FCAI.api.Repositories.CustomerRepo;
import com.example.FCAI.api.model.Customer;
import com.example.FCAI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api/customers")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);

    }

    @PostMapping ("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        if (createdCustomer != null) {
            return ResponseEntity.ok(createdCustomer);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Customer already exists");

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id,@RequestBody Customer customer) {

        Customer updatedCustomer = customerService.updateCustomer(id,customer);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        Customer deletedCustomer = customerService.deleteCustomer(id);
        if (deletedCustomer != null) {
            return ResponseEntity.ok(deletedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

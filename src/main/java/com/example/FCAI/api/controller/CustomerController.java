package com.example.FCAI.api.controller;
import com.example.FCAI.api.model.Customer.Customer;
import com.example.FCAI.api.model.Customer.LoggedInCustomer;
import com.example.FCAI.api.model.Order.Order;
import com.example.FCAI.api.model.Order.SimpleOrder;
import com.example.FCAI.api.model.RequestedProducts;
import com.example.FCAI.api.model.UserAuthResponses.LoginResponse;
import com.example.FCAI.api.model.UserAuthResponses.SignUpResponse;
import com.example.FCAI.service.CustomerService;
import com.example.FCAI.service.OrderService;
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
    private OrderService orderService;


    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String name, @RequestParam int balance) {
        Customer createdCustomer = customerService.signUp(name, balance);
        if (createdCustomer != null) {
            SignUpResponse signUpResponse = new SignUpResponse("Customer signed up successfully.", createdCustomer);
            return ResponseEntity.ok(signUpResponse);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Customer already exists");

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer logincustomer) {
        Customer loggedInCustomer = customerService.login(logincustomer);
        if (loggedInCustomer != null) {
            LoginResponse loginResponse = new LoginResponse("Customer logged in successfully", loggedInCustomer);
            return ResponseEntity.ok(loginResponse);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Invalid login credentials");

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

    //Placing Orders
    @PostMapping("/placeSimpleOrder")
    public ResponseEntity<?> placeSimpleOrder(@RequestBody List<RequestedProducts> products) {
        //To be wrapped inside CustomerService
        Customer loggedInCustomer = LoggedInCustomer.getLoggedInCustomer();
        if (loggedInCustomer == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Please login first");

            return ResponseEntity.badRequest().body(errorResponse);
        }
        // changed SimpleOrder to abstract Order
        Order simpleOrder = (Order)orderService.placeSimpleOrder(loggedInCustomer, products);
        if (simpleOrder != null) {
            System.out.println("simpleOrder is Not null");
            System.out.println("Printing the Response message");
            System.out.println(ResponseEntity.ok(simpleOrder));
            return ResponseEntity.ok(simpleOrder);
        } else {
            System.out.println("simpleOrder is null");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Cannot Place Order");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/placeCompoundOrder")
    public ResponseEntity<?> placeCompoundOrder(@RequestBody List<SimpleOrder> orders) {
        //To be wrapped inside CustomerService
        Customer loggedInCustomer = LoggedInCustomer.getLoggedInCustomer();
        if (loggedInCustomer == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Please login first");

            return ResponseEntity.badRequest().body(errorResponse);
        }

        Order compoundOrder = orderService.placeCompoundOrder(loggedInCustomer, orders);
        if (compoundOrder != null) {
            return ResponseEntity.ok(compoundOrder);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", "Cannot Place Order");

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }



}

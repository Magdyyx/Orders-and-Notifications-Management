package com.example.FCAI.api.model.UserAuthResponses;

import com.example.FCAI.api.model.Customer.Customer;

public class LoginResponse {
    private String message;
    private Customer loggedInCustomer;

    public LoginResponse(String message, Customer loggedInCustomer) {
        this.message = message;
        this.loggedInCustomer = loggedInCustomer;
    }


    public String getMessage() {
        return message;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLoggedInCustomer(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
    }
}

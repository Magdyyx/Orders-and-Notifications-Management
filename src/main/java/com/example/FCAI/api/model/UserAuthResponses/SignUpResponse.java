package com.example.FCAI.api.model.UserAuthResponses;

import com.example.FCAI.api.model.Customer.Customer;

public class SignUpResponse {
    private String message;
    private Customer SignedUpCustomer;
    public SignUpResponse(String message, Customer signedUpCustomer) {
        this.message = message;
        SignedUpCustomer = signedUpCustomer;

    }
    public String getMessage() {
        return message;
    }
    public Customer getSignedUpCustomer() {
        return SignedUpCustomer;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSignedUpCustomer(Customer signedUpCustomer) {
        SignedUpCustomer = signedUpCustomer;
    }

}

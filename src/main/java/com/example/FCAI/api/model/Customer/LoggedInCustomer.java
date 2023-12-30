package com.example.FCAI.api.model.Customer;

public class LoggedInCustomer extends Customer {

        int id;
        String name;
        double balance;

        static Customer LoggedInCustomer;

        public LoggedInCustomer(int id, String name, double balance, String District, String Address) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            this.district = District;
            this.address = Address;
            LoggedInCustomer = new Customer(id, name, balance, District, Address);
        }
        public LoggedInCustomer() {
        }

        //setters and getters
        public static Customer getLoggedInCustomer() {
        return LoggedInCustomer;
    }

            public static void setLoggedInCustomer(Customer loggedInCustomer) {
            LoggedInCustomer = loggedInCustomer;
        }

            public String details() {
                return "Customer{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", balance=" + balance +
                        '}';
            }


}

package com.example.FCAI.api.model.Customer;

public class LoggedInCustomer extends Customer {

        int id;
        String name;
        double balance;

        static Customer LoggedInCustomer;

        public LoggedInCustomer(int id, String name, double balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            LoggedInCustomer = new Customer(id, name, balance);
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

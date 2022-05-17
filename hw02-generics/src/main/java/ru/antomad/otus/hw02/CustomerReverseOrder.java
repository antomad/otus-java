package ru.antomad.otus.hw02;

import java.util.*;

public class CustomerReverseOrder {

    private final List<Customer> customers;

    CustomerReverseOrder() {
        customers = new ArrayList<>();
    }

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Customer take() {
        if (customers.isEmpty()) {
            return null;
        }
        return customers.remove(customers.size() - 1);
    }
}

package ru.antomad.otus.hw02;

import java.util.*;

public class CustomerService {

    private final NavigableMap<Customer, String> dictionary;

    public CustomerService() {
        this.dictionary = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = dictionary.entrySet().iterator().next();
        return getCopyOfEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> result = dictionary.ceilingEntry(customer);
        Iterator<Map.Entry<Customer, String>> iterator = dictionary.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(customer)) {
                result = iterator.next();
                break;
            }
        }
        return result != null ? getCopyOfEntry(result) : null;
    }

    public void add(Customer customer, String data) {
        dictionary.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopyOfEntry(Map.Entry<Customer, String> entry) {
        Map<Customer, String> copy = new HashMap<>();
        copy.put(new Customer(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getScores()),
                entry.getValue());
        return copy.entrySet().iterator().next();
    }
}

package me.truongng.journeymapapi.repository;

import java.util.List;

import me.truongng.journeymapapi.models.Customer;

public interface CustomerRespositoryInterface {
    boolean create(Customer customer);

    boolean update(Customer customer);

    List<Customer> findById(String id);
}

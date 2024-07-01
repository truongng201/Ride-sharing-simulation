package me.truongng.journeymapapi.repository;

import me.truongng.journeymapapi.models.Customer;

public interface CustomerRespositoryInterface {
    boolean create(Customer customer);

    boolean update(Customer customer);
}

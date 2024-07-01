package me.truongng.journeymapapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.User;

@Repository
public class CustomerRepository implements CustomerRespositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Customer customer) {
        int res = jdbcTemplate.update(
                "INSERT INTO customers (user_id, current_x, current_y) VALUES (?, ?, ?)",
                customer.getUserId(),
                (int) customer.getLocation().getX(),
                (int) customer.getLocation().getY());
        return res == 1 ? true : false;
    }

    @Override
    public boolean update(Customer customer) {
        int res = jdbcTemplate.update(
                "UPDATE customers SET user_id = ?, current_x = ? WHERE id = ?",
                customer.getUserId(),
                (int) customer.getLocation().getX(),
                (int) customer.getLocation().getY(),
                customer.getId());
        return res == 1 ? true : false;
    }

    @Override
    public List<Customer> findById(String id) {
        return jdbcTemplate.query(
                "SELECT user_id, current_x, current_y FROM customers WHERE id = ?",
                (rs, rowNum) -> new Customer(
                        new User(rs.getString("user_id"), null, null, null, null, null, null),
                        new Location(rs.getInt("current_x"), rs.getInt("current_y"))),
                Integer.parseInt(id));
    }
}
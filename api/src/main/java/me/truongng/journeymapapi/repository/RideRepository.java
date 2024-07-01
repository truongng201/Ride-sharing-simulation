package me.truongng.journeymapapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.Ride;

@Repository
public class RideRepository implements RideRespositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Ride ride) {
        int res = jdbcTemplate.update(
                "INSERT INTO rides (customer_id, driver_id, start_x, start_y, end_x, end_y, status, fare) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                ride.getCustomer().getId(),
                ride.getDriver().getId(),
                ride.getPickupLocation().getX(),
                ride.getPickupLocation().getY(),
                ride.getDropoffLocation().getX(),
                ride.getStatus(),
                ride.getFare());
        return res == 1 ? true : false;
    }

    @Override
    public boolean update(Ride ride) {
        int res = jdbcTemplate.update(
                "UPDATE rides SET customer_id = ?, driver_id = ?, start_x = ?, start_y = ?, end_x = ?, end_y = ?, status = ?, fare = ? WHERE id = ?",
                ride.getCustomer().getId(),
                ride.getDriver().getId(),
                ride.getPickupLocation().getX(),
                ride.getPickupLocation().getY(),
                ride.getDropoffLocation().getX(),
                ride.getDropoffLocation().getY(),
                ride.getStatus(),
                ride.getFare(),
                ride.getId());
        return res == 1 ? true : false;
    }
}
package me.truongng.journeymapapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Config;

@Repository
public class RideRepository implements RideRespositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Ride ride) {
        int res = jdbcTemplate.update(
                "INSERT INTO rides (customer_id, driver_id, start_x, start_y, end_x, end_y, status, fare) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Integer.parseInt(ride.getCustomer().getId()),
                ride.getDriver() == null ? 1 : Integer.parseInt(ride.getDriver().getId()), // 1 is admin
                ride.getPickupLocation().getX(),
                ride.getPickupLocation().getY(),
                ride.getDropoffLocation().getX(),
                ride.getDropoffLocation().getY(),
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

    @Override
    public List<Ride> getRidesByStatus(String status) {
        return jdbcTemplate.query(
                "SELECT id, customer_id, driver_id, start_x, start_y, end_x, end_y, status, fare FROM rides WHERE status = ?",
                (rs, rowNum) -> new Ride(
                        new Customer(new User(rs.getString("customer_id"), null, null, null, null, null, null),
                                new Location(rs.getInt("start_x"), rs.getInt("start_y"))),
                        null,
                        new Location(rs.getInt("start_x"), rs.getInt("start_y")),
                        new Location(rs.getInt("end_x"), rs.getInt("end_y")),
                        Config.RideStatus.valueOf(rs.getString("status")),
                        rs.getDouble("fare"),
                        rs.getDouble("distance")),
                status);
    }
}
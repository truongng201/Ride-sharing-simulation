package me.truongng.journeymapapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.Driver;

@Repository
public class DriverRepository implements DriverRespositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Driver driver) {
        int res = jdbcTemplate.update(
                "INSERT INTO drivers (user_id, current_x, current_y, vehicle_type, rating, revenue, kilometers_driven) VALUES (?, ?, ?, ?, ?, ?, ?)",
                driver.getUserId(),
                driver.getLocation().getX(),
                driver.getLocation().getY(),
                driver.getVehicleType(),
                driver.getRating(),
                driver.getRevenue(),
                driver.getKilometersDriven());
        return res == 1 ? true : false;
    }

    @Override
    public boolean update(Driver driver) {
        int res = jdbcTemplate.update(
                "UPDATE drivers SET user_id = ?, current_x = ?, current_y = ?, vehicle_type = ?, rating = ?, revenue = ?, kilometers_driven = ? WHERE id = ?",
                driver.getUserId(),
                driver.getLocation().getX(),
                driver.getLocation().getY(),
                driver.getVehicleType(),
                driver.getRating(),
                driver.getRevenue(),
                driver.getKilometersDriven(),
                driver.getId());
        return res == 1 ? true : false;
    }
}
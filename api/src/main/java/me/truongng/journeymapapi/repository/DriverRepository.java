package me.truongng.journeymapapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.models.Config;

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

    @Override
    public List<Driver> findById(String id) {
        return jdbcTemplate.query(
                "SELECT id, user_id, current_x, current_y, vehicle_type, rating, revenue, kilometers_driven FROM drivers WHERE id = ?",
                (rs, rowNum) -> new Driver(
                        rs.getString("id"),
                        new Location(rs.getDouble("current_x"), rs.getDouble("current_y")),
                        Config.VehicleType.valueOf(rs.getString("vehicle_type")),
                        new User(),
                        rs.getInt("rating"),
                        rs.getDouble("revenue"),
                        rs.getDouble("kilometers_driven")),
                Integer.parseInt(id));
    }

    @Override
    public List<Driver> getAllDriverNotRunning() {
        return jdbcTemplate.query(
                "SELECT drivers.id as driver_id, user_id, current_x, current_y, vehicle_type, rating, revenue, kilometers_driven FROM drivers LEFT JOIN rides ON drivers.id = rides.driver_id WHERE rides.id IS NULL",
                (rs, rowNum) -> new Driver(
                        rs.getString("driver_id"),
                        new Location(rs.getDouble("current_x"), rs.getDouble("current_y")),
                        Config.VehicleType.valueOf(rs.getString("vehicle_type")),
                        new User(),
                        rs.getInt("rating"),
                        rs.getDouble("revenue"),
                        rs.getDouble("kilometers_driven")));
    }
}
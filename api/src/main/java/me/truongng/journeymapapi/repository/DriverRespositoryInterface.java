package me.truongng.journeymapapi.repository;

import java.util.List;

import me.truongng.journeymapapi.models.Driver;

public interface DriverRespositoryInterface {
    boolean create(Driver driver);

    boolean update(Driver driver);

    List<Driver> findById(String id);

    List<Driver> getAllDriverNotRunning();

    List<Driver> findByUserId(String userId);
}

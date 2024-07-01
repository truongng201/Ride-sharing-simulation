package me.truongng.journeymapapi.repository;

import java.util.List;

import me.truongng.journeymapapi.models.Ride;

public interface RideRespositoryInterface {
    boolean create(Ride ride);

    boolean update(Ride ride);

    List<Ride> getRidesByDriverId(String driverId);

    List<Ride> findById(String rideId);
}
package me.truongng.journeymapapi.repository;

import me.truongng.journeymapapi.models.Ride;

public interface RideRespositoryInterface {
    boolean create(Ride ride);

    boolean update(Ride ride);
    
} 
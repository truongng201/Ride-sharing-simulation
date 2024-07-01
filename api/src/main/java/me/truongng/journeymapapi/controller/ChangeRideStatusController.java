package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.validation.ChangeRideStatusValidation;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.utils.exception.InternalServerErrorException;
import me.truongng.journeymapapi.utils.exception.NotFoundException;

@RestController
@RequestMapping("/change-ride-status")
public class ChangeRideStatusController {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private ChangeRideStatusValidation ChangeRideStatusValidation;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> ChangeRideStatus(@RequestBody Map<String, String> body) {
        String rideID = body.getOrDefault("ride_id", null);
        String newStatus = body.getOrDefault("new_status", null);

        ChangeRideStatusValidation.validate(rideID, newStatus);

        List<Ride> rides = rideRepository.findById(rideID);
        if (rides.size() == 0)
            throw new NotFoundException("Ride not found");

        Ride ride = rides.get(0);
        ride.setStatus(newStatus);
        System.out.println(ride);
        if (!rideRepository.update(ride))
            throw new InternalServerErrorException("Failed to update ride status");

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Change Ride Status Success!");
    }
}

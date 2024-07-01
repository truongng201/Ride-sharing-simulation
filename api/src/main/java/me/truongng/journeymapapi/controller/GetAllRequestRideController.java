package me.truongng.journeymapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.List;

import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Config.RideStatus;
import me.truongng.journeymapapi.utils.exception.NotFoundException;
import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/get-all-request-ride")
public class GetAllRequestRideController {
    @Autowired
    private RideRepository rideRepository;

    private Logger log = LoggerFactory.getLogger(BookDriveController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllRequestRide() {
        List<Ride> rides = rideRepository.getRidesByStatus(RideStatus.REQUESTED.name());
        for (Ride ride : rides) {
            log.info("Ride: " + ride);
        }
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Ride requested");
    }
}

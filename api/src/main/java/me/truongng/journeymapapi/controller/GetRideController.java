package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.Ride;

@RestController
@RequestMapping("/get-ride")
public class GetRideController {
    @Autowired
    private RideRepository rideRepository;
    private Logger log = LoggerFactory.getLogger(GetRideController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getRide(@RequestParam String driverId) {
        log.info("GetRide.request_payload: " + driverId);

        List<Ride> rides = rideRepository.getRidesByDriverId(driverId);
        if (rides.size() == 0) {
            return ResponseHandler.responseBuilder(HttpStatus.OK, "No ride available at the moment.");
        }

        Ride ride = rides.get(0);
        System.out.println(ride);
        return ResponseHandler.responseBuilder(HttpStatus.OK, ride);
    }
}
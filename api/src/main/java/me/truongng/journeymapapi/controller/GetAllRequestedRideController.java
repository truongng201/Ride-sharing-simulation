package me.truongng.journeymapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.List;

import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.Config.RideStatus;
import me.truongng.journeymapapi.utils.exception.NotFoundException;
import me.truongng.journeymapapi.utils.KMeansClustering;
import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/get-all-requested-ride")
public class GetAllRequestedRideController {
    @Autowired
    private DriverRepository driverRepository;

    private Logger log = LoggerFactory.getLogger(BookDriveController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllRequestedRide(@RequestParam String driverID) {
        log.info("GetAllRequestRideController.request_payload: " + driverID);

        List<Driver> drivers = driverRepository.getAllDriverNotRunning();

        boolean found = false;
        for (Driver driver : drivers) {
            if (driver.getId().equals(driverID)) {
                found = true;
            }
        }
        if (!found)
            throw new NotFoundException("Driver not found");
        int numberOfClusters = 3;
        KMeansClustering kMeans = new KMeansClustering(numberOfClusters, drivers);
        kMeans.run(100, 0.01);
        Map<Driver, List<Driver>> clusters = kMeans.getClusters();
        for (Map.Entry<Driver, List<Driver>> entry : clusters.entrySet()) {
            System.out.println(
                    "Centroid: " + entry.getKey().getLocation().getX() + ", " +
                            entry.getKey().getLocation().getY());
            for (Driver driver : entry.getValue()) {
                System.out.println("Driver: " + driver.getLocation().getX() + ", " +
                        driver.getLocation().getY());
            }
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Ride requested");
    }
}

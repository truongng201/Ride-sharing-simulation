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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.DriverSearch;
import me.truongng.journeymapapi.utils.KMeansClustering;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.validation.BookDriveValidation;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.Config.RideStatus;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.utils.exception.InternalServerErrorException;
import me.truongng.journeymapapi.utils.exception.NotFoundException;

@RestController
@RequestMapping("/book-drive")
public class BookDriveController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BookDriveValidation bookDriveValidation;

    private Logger log = LoggerFactory.getLogger(BookDriveController.class);

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> bookDrive(@RequestBody Map<String, String> body) {
        String endX = body.getOrDefault("end_x", null);
        String endY = body.getOrDefault("end_y", null);
        String customerID = body.getOrDefault("customer_id", null);

        log.info("BookDriveController.request_payload: " + " " + endX + " " + endY + " "
                + customerID);

        bookDriveValidation.validate(endX, endY, customerID);

        List<Customer> customers = customerRepository.findById(customerID);
        if (customers.size() == 0)
            throw new NotFoundException("Customer not found");
        Customer currCustomer = customers.get(0);
        currCustomer.setId(customerID);

        Double currX = currCustomer.getLocation().getX();
        Double currY = currCustomer.getLocation().getY();
        Double desX = Double.parseDouble(endX);
        Double desY = Double.parseDouble(endY);

        List<Driver> drivers = driverRepository.getAllDriverNotRunning();

        double maxClusterDistance = 30;
        KMeansClustering kMeans = new KMeansClustering(3, drivers);
        Map<Driver, List<Driver>> clusters = kMeans.getClusters(maxClusterDistance);
        DriverSearch driverSearch = new DriverSearch(clusters);

        Driver bestDriver = driverSearch.findBestDriver(currCustomer);
        System.out.println("Best driver: " + bestDriver);
        Ride ride = new Ride(currCustomer,
                bestDriver, new Location(currX, currY), new Location(desX, desY),
                RideStatus.REQUESTED, 0, 0);

        if (!rideRepository.create(ride))
            throw new InternalServerErrorException("Failed to book ride");

        return ResponseHandler.responseBuilder(HttpStatus.OK,
                "Successfully book a ride. Wait for driver to accept the ride");
    }
}

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

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.validation.BookDriveValidation;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.Config.RideStatus;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.RideRepository;
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

        Double currX = customers.get(0).getLocation().getX();
        Double currY = customers.get(0).getLocation().getY();
        Double desX = Double.parseDouble(endX);
        Double desY = Double.parseDouble(endY);

        customers.get(0).setId(customerID);
        Ride ride = new Ride(customers.get(0), null, new Location(currX, currY), new Location(desX, desY),
                RideStatus.REQUESTED, 0, 0);

        if (!rideRepository.create(ride))
            throw new InternalServerErrorException("Failed to book ride");

        return ResponseHandler.responseBuilder(HttpStatus.OK,
                "Successfully book a ride. Wait for driver to accept the ride");
    }
}

package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.DriverSearch;
import me.truongng.journeymapapi.utils.KMeansClustering;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.validation.CreateRideValidation;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.models.Ride;
import me.truongng.journeymapapi.models.Config.RideStatus;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.RideRepository;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.utils.exception.InternalServerErrorException;
import me.truongng.journeymapapi.utils.exception.NotFoundException;
import me.truongng.journeymapapi.utils.exception.UnauthorizedException;
import me.truongng.journeymapapi.utils.JwtTokenUtil;

@RestController
@RequestMapping("/create-ride")
public class CreateRideController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreateRideValidation CreateRideValidation;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger log = LoggerFactory.getLogger(CreateRideController.class);

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> CreateRide(@RequestBody Map<String, String> body) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            throw new UnauthorizedException("You are not authorized to access this resource");
        String token = authHeader.split(" ")[1];
        String email = jwtTokenUtil.extractPayload(token);

        if (email == null)
            throw new UnauthorizedException("You are not authorized to access this resource");

        List<User> users = userRepository.findByEmail(email);
        if (users.size() == 0)
            throw new NotFoundException("User not found");
        User currUser = users.get(0);
        String endX = body.getOrDefault("end_x", null);
        String endY = body.getOrDefault("end_y", null);

        log.info("CreateRideController.request_payload: " + " " + endX + " " + endY + " "
                + email);

        CreateRideValidation.validate(endX, endY);

        List<Customer> customers = customerRepository.findByUserId(currUser.getId());
        if (customers.size() == 0)
            throw new NotFoundException("Customer not found");
        Customer currCustomer = customers.get(0);

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
        Ride ride = new Ride(null, currCustomer,
                bestDriver, new Location(currX, currY), new Location(desX, desY),
                RideStatus.REQUESTED, 0, 0);

        if (!rideRepository.create(ride))
            throw new InternalServerErrorException("Failed to book ride");
        System.out.println("Ride created: " + ride);
        System.out.println("Best driver: " + bestDriver);
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Ride created");
    }
}

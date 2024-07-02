package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.DriverSearch;
import me.truongng.journeymapapi.utils.JwtTokenUtil;
import me.truongng.journeymapapi.utils.KMeansClustering;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.utils.exception.NotFoundException;
import me.truongng.journeymapapi.utils.exception.UnauthorizedException;

@RestController
@RequestMapping("/all-best-drivers")
public class GetAllBestDriversController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger log = LoggerFactory.getLogger(GetAllBestDriversController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            throw new UnauthorizedException("You are not authorized to access this resource");
        String token = authHeader.split(" ")[1];
        String email = jwtTokenUtil.extractPayload(token);

        log.info("GetAllBestDriversController.request_payload: " + email);

        if (email == null)
            throw new NotFoundException("Invalid token");

        List<User> users = userRepository.findByEmail(email);
        if (users.size() == 0)
            throw new NotFoundException("User not found");
        User currUser = users.get(0);

        List<Customer> customers = customerRepository.findByUserId(currUser.getId());
        if (customers.size() == 0)
            throw new NotFoundException("Customer not found");

        Customer currCustomer = customers.get(0);

        List<Driver> drivers = driverRepository.getAllDriverNotRunning();

        double maxClusterDistance = 30;
        KMeansClustering kMeans = new KMeansClustering(3, drivers);
        Map<Driver, List<Driver>> clusters = kMeans.getClusters(maxClusterDistance);
        DriverSearch driverSearch = new DriverSearch(clusters);

        List<Driver> bestDrivers = driverSearch.getAllBestDrivers(currCustomer);
        List<int[]> bestDriversLocation = new ArrayList<>();
        for (Driver driver : bestDrivers) {
            bestDriversLocation.add(new int[] { (int) driver.getLocation().getX(), (int) driver.getLocation().getY() });
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, bestDriversLocation);
    }
}

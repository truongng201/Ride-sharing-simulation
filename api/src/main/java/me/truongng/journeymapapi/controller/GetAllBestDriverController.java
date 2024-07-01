package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.DriverSearch;
import me.truongng.journeymapapi.utils.KMeansClustering;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.utils.exception.NotFoundException;

@RestController
@RequestMapping("/get-all-best-driver")
public class GetAllBestDriverController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private Logger log = LoggerFactory.getLogger(BookDriveController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> bookDrive(@RequestParam String customerID) {
        log.info("GetAllBestDriver.request_payload: " + customerID);

        if (customerID == null)
            throw new NotFoundException("Customer ID is required");

        List<Customer> customers = customerRepository.findById(customerID);
        if (customers.size() == 0)
            throw new NotFoundException("Customer not found");
        Customer currCustomer = customers.get(0);
        currCustomer.setId(customerID);

        List<Driver> drivers = driverRepository.getAllDriverNotRunning();

        int numberOfClusters = 3;
        KMeansClustering kMeans = new KMeansClustering(numberOfClusters, drivers);
        kMeans.run(100, 0.01);
        Map<Driver, List<Driver>> clusters = kMeans.getClusters();
        DriverSearch driverSearch = new DriverSearch(clusters);

        List<Driver> bestDrivers = driverSearch.getAllBestDrivers(currCustomer);

        List<int[]> bestDriversLocation = new ArrayList<>();
        for (Driver driver : bestDrivers) {
            bestDriversLocation.add(new int[] { (int) driver.getLocation().getX(), (int) driver.getLocation().getY() });
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, bestDriversLocation);
    }
}

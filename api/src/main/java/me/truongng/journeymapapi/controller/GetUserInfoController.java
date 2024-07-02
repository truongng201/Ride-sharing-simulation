package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.utils.JwtTokenUtil;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.utils.exception.*;

@RestController
@RequestMapping("/user-info")
public class GetUserInfoController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final Logger log = LoggerFactory.getLogger(GetUserInfoController.class);

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            throw new UnauthorizedException("You are not authorized to access this resource");
        String token = authHeader.split(" ")[1];
        String email = jwtTokenUtil.extractPayload(token);
        log.info("Getting user info for user with email: " + email);
        List<User> users = userRepository.findByEmail(email);

        if (users.size() == 0)
            throw new NotFoundException("User not found");
        User user = users.get(0);
        HashMap<String, Object> response = new HashMap<>();
        response.put("user", user);
        System.out.println(user);

        if (user.getRole().equals("DRIVER")) {
            List<Driver> drivers = driverRepository.findByUserId(user.getId());
            if (drivers.size() == 0)
                throw new NotFoundException("Driver not found");
            System.out.println(drivers.get(0));
            response.put("driver", drivers.get(0));
        } else if (user.getRole().equals("CUSTOMER")) {
            List<Customer> customers = customerRepository.findByUserId(user.getId());
            if (customers.size() == 0)
                throw new NotFoundException("Customer not found");
            response.put("customer", customers.get(0));
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }
}

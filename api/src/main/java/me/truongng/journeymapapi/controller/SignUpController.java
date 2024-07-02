package me.truongng.journeymapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.models.Config;
import me.truongng.journeymapapi.models.Customer;
import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.repository.CustomerRepository;
import me.truongng.journeymapapi.repository.DriverRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.PasswordHasher;
import me.truongng.journeymapapi.utils.RandomLocationGen;
import me.truongng.journeymapapi.utils.exception.*;
import me.truongng.journeymapapi.validation.SignUpValidation;

@RestController
@RequestMapping("/auth")
public class SignUpController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private SignUpValidation signUpValidation;

    private Logger log = LoggerFactory.getLogger(SignInController.class);

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", null);
        String password = body.getOrDefault("password", null);
        String username = body.getOrDefault("username", null);
        String role = body.getOrDefault("role", null);
        String vehicleType = body.getOrDefault("vehicle_type", null);

        log.info("SignUpController.request_payload: " + email + " " + password + " " + username + " " + role + " "
                + vehicleType);

        signUpValidation.validate(email, password, username, role, vehicleType);

        List<User> users = userRepository.findByEmail(email);
        if (users.size() > 0)
            throw new BadRequestException("Email is already taken");

        String hashedPassword = PasswordHasher.hashPassword(password);

        User user = new User(
                null,
                username,
                email,
                hashedPassword,
                "https://api.dicebear.com/6.x/bottts-neutral/svg?seed=" + email,
                false,
                Config.Role.valueOf(role));
        if (user.getRole() == Config.Role.ADMIN.toString())
            throw new ForbiddenException("Admin cannot be created");

        if (!userRepository.create(user))
            throw new InternalServerErrorException("Failed to create user");

        User currUser = userRepository.findByEmail(email).get(0);

        Location randomLocation = RandomLocationGen.generateRandomLocation();

        if (user.getRole() == Config.Role.CUSTOMER.toString()) {
            // create customer
            Customer customer = new Customer(currUser, randomLocation);
            if (!customerRepository.create(customer))
                throw new InternalServerErrorException("Failed to create customer");
        }

        if (user.getRole() == Config.Role.DRIVER.toString()) {
            // create driver
            Driver driver = new Driver(null,
                    randomLocation, Config.VehicleType.valueOf(vehicleType), currUser, 0,
                    0.0, 0.0);
            if (!driverRepository.create(driver))
                throw new InternalServerErrorException("Failed to create driver");
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, "User created");
    }

}
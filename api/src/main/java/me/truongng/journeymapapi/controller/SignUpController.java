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
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.PasswordHasher;
import me.truongng.journeymapapi.utils.exception.*;
import me.truongng.journeymapapi.validation.SignUpValidation;

@RestController
@RequestMapping("/auth")
public class SignUpController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SignUpValidation signUpValidation;

    private Logger log = LoggerFactory.getLogger(SignInController.class);

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", null);
        String password = body.getOrDefault("password", null);
        String username = body.getOrDefault("username", null);
        String role = body.getOrDefault("role", null);

        log.info("SignUpController.request_payload: " + email + " " + password + " " + username + " " + role);

        signUpValidation.validate(email, password, username, role);

        List<User> users = userRepository.findByEmail(email);
        if (users.size() > 0)
            throw new BadRequestException("Email is already taken");

        String hashedPassword = PasswordHasher.hashPassword(password);

        User user = new User(
                null,
                username,
                email,
                hashedPassword,
                null,
                false,
                Config.Role.valueOf(role));

        if (!userRepository.create(user))
            throw new InternalServerErrorException("Failed to create user");

        if (user.getRole() == Config.Role.ADMIN.toString())
            throw new ForbiddenException("Admin cannot be created");

        if (user.getRole() == Config.Role.CUSTOMER.toString()) {
            // create customer
        }

        if (user.getRole() == Config.Role.DRIVER.toString()) {
            // create driver
        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, "User created");
    }

}
package me.truongng.journeymapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.models.RefreshToken;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.repository.RefreshTokenRepository;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.exception.*;
import me.truongng.journeymapapi.validation.SignInValidation;
import me.truongng.journeymapapi.utils.PasswordHasher;

@RestController
@RequestMapping("/api/auth")
public class SignInController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private SignInValidation signInValidation;

    private Logger log = LoggerFactory.getLogger(SignInController.class);

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", null);
        String password = body.getOrDefault("password", null);

        log.info("SignInController.request_payload: " + email + " " + password);

        signInValidation.validate(email, password);

        List<User> users = userRepository.findByEmail(email);
        if (users.size() == 0)
            throw new NotFoundException("User not found");

        User user = users.get(0);
        if (!PasswordHasher.checkPassword(password, user.getPassword()))
            throw new UnauthorizedException("Invalid password");

        RefreshToken refreshToken = new RefreshToken("random_refresh_token", user);

        if (!refreshTokenRepository.create(refreshToken))
            throw new InternalServerErrorException("Failed to create refresh token");

        HashMap<String, Object> response = new HashMap<>() {
            {
                put("access_token", "random_access_token");
                put("refreshToken", "random_refresh_token");
            }
        };
        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }

}
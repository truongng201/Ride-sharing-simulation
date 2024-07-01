package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.exception.NotFoundException;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.validation.ForgotPasswordValidation;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ForgotPasswordValidation forgotPasswordValidation;

    private Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", null);

        log.info("ForgotPasswordController.request_payload: " + email);
        forgotPasswordValidation.validate(email);

        if (userRepository.findByEmail(email).size() == 0)
            throw new NotFoundException("Email not found");

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Forgot password endpoint hit");
    }
}
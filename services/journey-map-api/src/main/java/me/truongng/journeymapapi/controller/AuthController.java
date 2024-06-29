package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.repository.RefreshTokenRepository;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.models.RefreshToken;
import me.truongng.journeymapapi.models.Config;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(
            @RequestBody Map<String, String> body) {
        try {
            String email = body.getOrDefault("email", null);
            String password = body.getOrDefault("password", null);
            if (email == null || password == null) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.BAD_REQUEST,
                        "Email and password are required");
            }

            List<User> users = userRepository.findByEmail(email);
            if (users.size() == 0) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password");
            }

            User user = users.get(0);
            System.out.println(user);
            
            if (!user.getPassword().equals(password)) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password");
            }

            RefreshToken refreshToken = new RefreshToken(
                    "random_refresh_token",
                    null,
                    null,
                    null,
                    user);

            boolean res = refreshTokenRepository.create(refreshToken);
            if (!res) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create refresh token");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK,
                    new HashMap<>() {
                        {
                            put("access_token", "random_access_token");
                            put("refresh_token", "random_refresh_token");
                        }

                    });
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            String username = body.get("username");
            String role = body.get("role");
            if (email.equals("") || password.equals("") || username.equals("") || role.equals("")) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.BAD_REQUEST,
                        "Email, password, and username are required");
            }

            List<User> users = userRepository.findByEmail(email);
            if (users.size() > 0) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.BAD_REQUEST,
                        "Email is already taken");
            }

            User user = new User(
                    username,
                    email,
                    password,
                    null,
                    false,
                    Config.Role.valueOf(role));

            boolean res = userRepository.create(user);
            if (!res) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create user");
            }

            return ResponseHandler.responseBuilder(
                    HttpStatus.CREATED,
                    null);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<Map<String, Object>> signout() {
        return ResponseHandler.responseBuilder(
                HttpStatus.OK,
                null);
    }

    @GetMapping("/fogot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword() {
        return ResponseHandler.responseBuilder(
                HttpStatus.OK,
                null);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword() {
        return ResponseHandler.responseBuilder(
                HttpStatus.OK,
                null);
    }
}

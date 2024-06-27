package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin() {
        return ResponseHandler.responseBuilder(
                "Login successfully",
                HttpStatus.OK.value(),
                null);
    }

    @GetMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup() {
        return ResponseHandler.responseBuilder(
                "Create new account successfully",
                HttpStatus.CREATED.value(),
                null);
    }

    @GetMapping("/signout")
    public ResponseEntity<Map<String, Object>> signout() {
        return ResponseHandler.responseBuilder(
                "Log out successfully",
                HttpStatus.OK.value(),
                null);
    }

    @GetMapping("/fogot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword() {
        return ResponseHandler.responseBuilder(
                "Reset password email sent",
                HttpStatus.OK.value(),
                null);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword() {
        return ResponseHandler.responseBuilder(
                "Reset password success",
                HttpStatus.OK.value(),
                null);
    }
}

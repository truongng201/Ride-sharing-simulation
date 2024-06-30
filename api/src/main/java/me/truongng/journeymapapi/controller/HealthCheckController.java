package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseHandler.responseBuilder(
                HttpStatus.OK, "Journey Map API is running");
    }
}

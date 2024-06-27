package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseHandler.responseBuilder("Service is up", HttpStatus.OK.value(), null);
    }
}

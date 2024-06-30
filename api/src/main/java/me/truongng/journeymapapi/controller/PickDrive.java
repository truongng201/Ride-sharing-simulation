package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/pick-drive")
public class PickDrive {
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> pickDrive() {
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Pick Drive API is hit");
    }
}

package me.truongng.journeymapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import me.truongng.journeymapapi.utils.ResponseHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class ResetPasswordController {
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Map<String, String> body) {
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Reset password endpoint hit");
    }
}
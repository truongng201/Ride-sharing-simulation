package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/book-drive")
public class BookDrive {
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> bookDrive() {
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Book Drive API is hit");
    }
}

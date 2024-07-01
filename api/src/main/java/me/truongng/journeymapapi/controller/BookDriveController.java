package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.ResponseHandler;

@RestController
@RequestMapping("/book-drive")
public class BookDriveController {
    private Logger log = LoggerFactory.getLogger(BookDriveController.class);

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> bookDrive() {
        log.info("Book Drive API is hit");
        // String currX = body.getOrDefault(currX, 0);
        // String currY = body.getOrDefault(currY, 0);

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Book Drive API is hit");
    }
}

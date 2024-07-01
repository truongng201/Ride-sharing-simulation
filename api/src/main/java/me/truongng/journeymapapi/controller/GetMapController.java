package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.GenMap;

@RestController
@RequestMapping("/get-map")
public class GetMapController {

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getMap() {
        int[][] map = GenMap.getMap();
        return ResponseHandler.responseBuilder(HttpStatus.OK, map);
    }
}

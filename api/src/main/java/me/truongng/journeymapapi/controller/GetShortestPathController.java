package me.truongng.journeymapapi.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.exception.BadRequestException;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.utils.GenMap;
import me.truongng.journeymapapi.utils.Journey;

@RestController
@RequestMapping("/get-shortest-path")
public class GetShortestPathController {
    private Logger log = LoggerFactory.getLogger(GetShortestPathController.class);

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> getShortestPath(@RequestBody Map<String, String> body) {
        String startX = body.getOrDefault("startX", null);
        String startY = body.getOrDefault("startY", null);
        String endX = body.getOrDefault("endX", null);
        String endY = body.getOrDefault("endY", null);

        log.info("GetShortestPathController.request_payload: " + startX + " " + startY + " " + endX + " " + endY);

        if (startX == null || startY == null || endX == null || endY == null)
            throw new BadRequestException("Missing required fields");

        Double currX = Double.parseDouble(startX);
        Double currY = Double.parseDouble(startY);
        Double desX = Double.parseDouble(endX);
        Double desY = Double.parseDouble(endY);

        Journey Journey = new Journey(new Location(currX, currY));
        GenMap.genGraph();
        ArrayList<Location> Order = Journey.OrderOfLocations(new ArrayList<>() {
            {
                add(new Location(desX, desY));
            }

        });
        for (Location p : Order) {
            System.out.println(p.getX() + " " + p.getY());
        }
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Get Shortest Path API is hit");
    }
}

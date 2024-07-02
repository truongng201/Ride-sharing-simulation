package me.truongng.journeymapapi.controller;

import java.util.ArrayList;
import java.util.List;
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
import me.truongng.journeymapapi.utils.ShortestPathTwoLocations;
import me.truongng.journeymapapi.utils.exception.BadRequestException;
import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.utils.GenMap;

@RestController
@RequestMapping("/find-shortest-path")
public class FindShortestPathController {
    private Logger log = LoggerFactory.getLogger(FindShortestPathController.class);

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> FindShortestPath(@RequestBody Map<String, String> body) {
        String startX = body.getOrDefault("startX", null);
        String startY = body.getOrDefault("startY", null);
        String endX = body.getOrDefault("endX", null);
        String endY = body.getOrDefault("endY", null);

        log.info("FindShortestPathController.request_payload: " + startX + " " + startY + " " + endX + " " + endY);

        if (startX == null || startY == null || endX == null || endY == null)
            throw new BadRequestException("Missing required fields");

        Double currX = Double.parseDouble(startX);
        Double currY = Double.parseDouble(startY);
        Double desX = Double.parseDouble(endX);
        Double desY = Double.parseDouble(endY);
        GenMap.genGraph();
        List<Location> paths = ShortestPathTwoLocations.getPath(new Location(currX, currY), new Location(desX, desY));
        List<int[]> res = new ArrayList<>();
        for (Location loc : paths) {
            res.add(new int[] { (int) loc.getX(), (int) loc.getY() });
            log.info("FindShortestPathController.response_payload: " + (int) loc.getX() + " " + (int) loc.getY());
        }
        return ResponseHandler.responseBuilder(HttpStatus.OK, res);
    }
}

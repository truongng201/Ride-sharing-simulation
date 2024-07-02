package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.models.Location;
import me.truongng.journeymapapi.utils.GenMap;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.ShortestPathTwoLocations;
import me.truongng.journeymapapi.utils.Journey;

@RestController
@RequestMapping("/ride-sharing")
public class RideSharingController {
    private static final Logger log = LoggerFactory.getLogger(RideSharingController.class);

    @SuppressWarnings("unchecked")
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> rideSharing(@RequestBody Map<String, Object> body) {
        int start_x = (int) body.getOrDefault("start_x", -1);
        int start_y = (int) body.getOrDefault("start_y", -1);
        Object endPoints = body.getOrDefault("end_points", new ArrayList<>());
        log.info("RideSharingController.request_payload: " + start_x + " " + start_y + " " + endPoints);

        if (start_x == -1 || start_y == -1 || ((List<Object>) endPoints).size() == 0)
            return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Missing required fields");

        GenMap.genGraph();
        Journey journey = new Journey(new Location(start_x, start_y));

        ArrayList<Location> pickupLocations = new ArrayList<>();
        // convert endPoints to ArrayList<Location>
        for (Object endPoint : (List<Object>) endPoints) {
            List<Integer> endPointList = (List<Integer>) endPoint;
            pickupLocations.add(new Location(endPointList.get(0), endPointList.get(1)));
        }

        ArrayList<Location> pickUpOrder = journey.OrderOfLocations(pickupLocations);
        ArrayList<int[]> res = new ArrayList<>();
        pickUpOrder.addFirst(new Location(start_x, start_y));
        for (int i = 0; i < pickUpOrder.size() - 1; i++) {
            ShortestPathTwoLocations.getPath(pickUpOrder.get(i), pickUpOrder.get(i + 1)).forEach(loc -> {
                res.add(new int[] { (int) loc.getX(), (int) loc.getY() });
            });
        }
        return ResponseHandler.responseBuilder(HttpStatus.OK, res);
    }
}

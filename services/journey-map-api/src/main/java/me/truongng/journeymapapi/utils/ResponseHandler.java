package me.truongng.journeymapapi.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class ResponseHandler {
    private static HashSet<HttpStatus> successResponse = new HashSet<>() {
        {
            add(HttpStatus.OK);
            add(HttpStatus.CREATED);
        }
    };
    private static HashSet<HttpStatus> errorResponse = new HashSet<>() {
        {
            add(HttpStatus.BAD_REQUEST);
            add(HttpStatus.UNAUTHORIZED);
            add(HttpStatus.FORBIDDEN);
            add(HttpStatus.NOT_FOUND);
            add(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    public static ResponseEntity<Map<String, Object>> responseBuilder(
            HttpStatus httpStatus,
            Object responseObject) {

        Map<String, Object> response = new HashMap<>();

        if (successResponse.contains(httpStatus)) {
            response.put("status", "success");
            response.put("data", responseObject);
        } else if (errorResponse.contains(httpStatus)) {
            response.put("status", "error");
            response.put("message", responseObject);
        }

        return new ResponseEntity<>(response, httpStatus);
    }

}

package me.truongng.journeymapapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class ResponseHandler {
    public static ResponseEntity<Map<String, Object>> responseBuilder(
            String message,
            int httpStatusCode,
            Object responseObject) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status_code", httpStatusCode);
        List<Object> data = new ArrayList<>();
        if (responseObject != null)
            data.add(responseObject);

        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
    }

}

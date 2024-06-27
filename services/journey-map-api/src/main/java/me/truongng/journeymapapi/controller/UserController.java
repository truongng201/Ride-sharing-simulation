package me.truongng.journeymapapi.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.User;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/:id")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable("id") String id) {
        try {
            List<User> users = userRepository.findById(id);

            if (users.size() == 0) {
                return ResponseHandler.responseBuilder(
                        HttpStatus.NOT_FOUND, "User not found");
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("user", users.get(0));

            return ResponseHandler.responseBuilder(
                    HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

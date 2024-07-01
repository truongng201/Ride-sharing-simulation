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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.repository.UserRepository;
import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.models.User;
import me.truongng.journeymapapi.utils.exception.*;

@RestController
@RequestMapping("/user")
public class GetUserInfoController {
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(GetUserInfoController.class);

    @GetMapping("/:id")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable("id") String id) {
        log.info("Getting user info for user with id: " + id);
        List<User> users = userRepository.findById(id);

        if (users.size() == 0)
            throw new NotFoundException("User not found");

        HashMap<String, Object> response = new HashMap<>();
        response.put("user", users.get(0));

        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }
}

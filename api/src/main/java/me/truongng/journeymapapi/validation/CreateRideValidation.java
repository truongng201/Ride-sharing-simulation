package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.utils.exception.BadRequestException;

@Service
public class CreateRideValidation {
    public void validate(String endX, String endY) {
        if (endX == null || endY == null) {
            throw new BadRequestException("Missing required fields");
        }
    }
}
package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.utils.exception.BadRequestException;

@Service
public class BookDriveValidation {
    public void validate(String endX, String endY, String customerId) {
        if (endX == null || endY == null || customerId == null) {
            throw new BadRequestException("Missing required fields");
        }
    }
}
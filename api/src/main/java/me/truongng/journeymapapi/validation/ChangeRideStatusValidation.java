package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.models.Config;
import me.truongng.journeymapapi.utils.exception.BadRequestException;

@Service
public class ChangeRideStatusValidation {
    public void validate(String rideId, String newStatus) {
        if (rideId == null || newStatus == null) {
            throw new BadRequestException("Missing required fields");
        }

        if (!Config.isValidRideStatus(newStatus)) {
            throw new BadRequestException("Invalid ride status");
        }

    }
}
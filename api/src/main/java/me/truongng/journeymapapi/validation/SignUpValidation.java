package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.utils.exception.BadRequestException;
import me.truongng.journeymapapi.models.Config;

@Service
public class SignUpValidation {
    public void validate(String email, String password, String username, String role, String vehicleType) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Password is required");
        }

        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Username is required");
        }

        if (role == null || role.isEmpty()) {
            throw new BadRequestException("Role is required");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new BadRequestException("Invalid email");
        }

        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters");
        }

        if (password.length() > 50) {
            throw new BadRequestException("Password must be at most 50 characters");
        }

        if (username.length() < 3) {
            throw new BadRequestException("Username must be at least 3 characters");
        }

        if (username.length() > 50) {
            throw new BadRequestException("Username must be at most 50 characters");
        }

        if (!Config.isValidRole(role)) {
            throw new BadRequestException("Invalid role");
        }

        if (role.equals("DRIVER") && (vehicleType == null || vehicleType.isEmpty())) {
            throw new BadRequestException("Vehicle type is required");
        }

        if (role.equals("DRIVER") && !Config.isValidVehicleType(vehicleType)) {
            throw new BadRequestException("Invalid vehicle type");
        }
    }
}

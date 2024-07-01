package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.utils.exception.BadRequestException;

@Service
public class ForgotPasswordValidation {
    public void validate(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new BadRequestException("Invalid email or password");
        }
    }
}

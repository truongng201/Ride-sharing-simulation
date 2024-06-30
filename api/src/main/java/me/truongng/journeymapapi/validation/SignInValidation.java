package me.truongng.journeymapapi.validation;

import org.springframework.stereotype.Service;

import me.truongng.journeymapapi.utils.exception.BadRequestException;

@Service
public class SignInValidation {
    public void validate(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Invalid email or password");
        }

        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new BadRequestException("Invalid email or password");
        }

        if (password.length() < 8) {
            throw new BadRequestException("Invalid email or password");
        }

        if (password.length() > 50) {
            throw new BadRequestException("Invalid email or password");
        }

        if (password.matches("^[a-zA-Z0-9]")) {
            throw new BadRequestException("Invalid email or password");
        }

    }
}

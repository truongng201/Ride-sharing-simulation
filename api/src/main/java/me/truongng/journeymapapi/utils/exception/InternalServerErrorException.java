package me.truongng.journeymapapi.utils.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ApiException {
    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
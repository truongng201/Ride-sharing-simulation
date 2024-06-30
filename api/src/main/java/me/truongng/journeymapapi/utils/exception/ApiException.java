package me.truongng.journeymapapi.utils.exception;

import org.springframework.http.HttpStatus;

class ApiException extends RuntimeException {
    private HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

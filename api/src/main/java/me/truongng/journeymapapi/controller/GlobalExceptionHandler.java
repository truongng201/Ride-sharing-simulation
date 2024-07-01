package me.truongng.journeymapapi.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.truongng.journeymapapi.utils.ResponseHandler;
import me.truongng.journeymapapi.utils.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        return ResponseHandler.responseBuilder(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        return ResponseHandler.responseBuilder(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage());

    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        return ResponseHandler.responseBuilder(
                HttpStatus.FORBIDDEN,
                ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseHandler.responseBuilder(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerError(InternalServerErrorException ex) {
        return ResponseHandler.responseBuilder(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        log.error("GlobalExceptionHandler.handleException: " + ex.getMessage() + " - ");
        ex.printStackTrace();
        return ResponseHandler.responseBuilder(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());
    }
}

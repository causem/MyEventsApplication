package com.example.MyEvents.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorDto(String error, String message, int status, Instant timestamp) {}

    private ResponseEntity<ErrorDto> build(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorDto(error, message, status.value(), Instant.now()));
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleEventNotFound(EventNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "EVENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(RegistrationFullException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleRegistrationFull(RegistrationFullException ex) {
        return build(HttpStatus.CONFLICT, "REGISTRATION_FULL", ex.getMessage());
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAlreadyRegistered(AlreadyRegisteredException ex) {
        return build(HttpStatus.CONFLICT, "ALREADY_REGISTERED", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleGeneral(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex.getMessage());
    }
}

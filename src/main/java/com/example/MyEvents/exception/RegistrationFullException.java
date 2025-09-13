package com.example.MyEvents.exception;

public class RegistrationFullException extends RuntimeException {
    public RegistrationFullException(Long eventId) {
        super("Event with id=" + eventId + " has no available seats");
    }
}
package com.example.MyEvents.exception;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(Long eventId, Long participantId) {
        super("Registration not found for eventId=" + eventId + " and participantId=" + participantId);
    }
}

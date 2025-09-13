package com.example.MyEvents.exception;

public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(Long eventId, Long participantId) {
        super("Participant with id=" + participantId + " is already registered for event " + eventId);
    }
}
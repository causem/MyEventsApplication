package com.example.MyEvents.exception;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(Long id) {
        super("Participant with id=" + id + " not found");
    }

    public ParticipantNotFoundException(String email) {
        super("Participant with email=" + email + " not found");
    }
}
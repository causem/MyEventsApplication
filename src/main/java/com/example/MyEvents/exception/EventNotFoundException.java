package com.example.MyEvents.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Event with id=" + id + " not found");
    }
}
package com.example.MyEvents.dto;

public record SeatsStatusDto(
        Long eventId,
        int capacity,
        long takenSeats,
        int availableSeats,
        boolean hasFreeSeats
) {}

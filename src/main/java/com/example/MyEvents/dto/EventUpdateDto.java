package com.example.MyEvents.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record EventUpdateDto(
        String name,
        String description,
        @Future LocalDateTime date,
        @Min(1) Integer capacity,
        Long locationId
) {}

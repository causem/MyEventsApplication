package com.example.MyEvents.dto;

import java.time.LocalDateTime;

public record EventResponseDto(
        Long id,
        String name,
        String description,
        LocalDateTime date,
        int capacity,
        Long locationId,
        String locationName,
        String locationCity,
        String locationAddress
) {}
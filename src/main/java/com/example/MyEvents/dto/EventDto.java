package com.example.MyEvents.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventDto(
        @NotBlank String name,
        String description,
        @NotNull @Future LocalDateTime date,
        @Min(1) int capacity,
        @NotNull Long locationId
) {}
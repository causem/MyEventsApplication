package com.example.MyEvents.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventCreateDto(
        @NotBlank String name,
        String description,
        @NotNull @Future LocalDateTime date,
        @Min(1) int capacity,
        Long locationId,
        LocationDto location
) {
    @AssertTrue(message = "Provide either locationId or location object, but not both")
    public boolean isExactlyOneLocationSource() {
        boolean hasId = locationId != null;
        boolean hasObj = location != null;
        return hasId ^ hasObj;
    }
}

package com.example.MyEvents.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationDto(
        @NotBlank String name,
        @NotBlank String city,
        @NotBlank String address
) {}

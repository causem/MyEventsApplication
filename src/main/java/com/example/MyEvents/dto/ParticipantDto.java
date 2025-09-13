package com.example.MyEvents.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticipantDto(
        @NotBlank String name,
        @Email @NotBlank String email
) {}
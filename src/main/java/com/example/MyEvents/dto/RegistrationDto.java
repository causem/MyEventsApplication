package com.example.MyEvents.dto;

import jakarta.validation.constraints.NotNull;

public record RegistrationDto(
        @NotNull Long eventId,
        @NotNull Long participantId
) {}

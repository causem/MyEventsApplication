package com.example.MyEvents.dto;

import java.time.LocalDateTime;

public record RegistrationResponseDto(
        Long id,
        Long eventId,
        Long participantId,
        LocalDateTime registrationDate
) {}
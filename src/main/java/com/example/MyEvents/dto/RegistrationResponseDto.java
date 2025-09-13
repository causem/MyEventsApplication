package com.example.MyEvents.dto;

import java.time.LocalDateTime;

public record RegistrationResponseDto(
        Long id,
        Long eventId,
        Long participantId,
        String participantName,
        String participantEmail,
        LocalDateTime registrationDate
) {}
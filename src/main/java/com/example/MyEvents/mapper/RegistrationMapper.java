package com.example.MyEvents.mapper;

import com.example.MyEvents.dto.RegistrationResponseDto;
import com.example.MyEvents.model.Registration;

public class RegistrationMapper {

    public static RegistrationResponseDto toDto(Registration r) {
        return new RegistrationResponseDto(
                r.getId(),
                r.getEvent().getId(),
                r.getParticipant().getId(),
                r.getRegistrationDate()
        );
    }
}
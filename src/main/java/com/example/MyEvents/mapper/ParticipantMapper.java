package com.example.MyEvents.mapper;

import com.example.MyEvents.dto.ParticipantDto;
import com.example.MyEvents.dto.ParticipantResponseDto;
import com.example.MyEvents.model.Participant;

public class ParticipantMapper {

    public static Participant toEntity(ParticipantDto dto) {
        Participant p = new Participant();
        p.setName(dto.name());
        p.setEmail(dto.email());
        return p;
    }

    public static ParticipantResponseDto toDto(Participant p) {
        return new ParticipantResponseDto(
                p.getId(),
                p.getName(),
                p.getEmail()
        );
    }
}

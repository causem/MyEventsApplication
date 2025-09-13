package com.example.MyEvents.service;

import com.example.MyEvents.dto.ParticipantDto;
import com.example.MyEvents.dto.ParticipantResponseDto;
import com.example.MyEvents.model.Participant;

import java.util.List;

public interface ParticipantService {
  List<ParticipantResponseDto> getAll();
  ParticipantResponseDto getById(Long id);
  ParticipantResponseDto create(ParticipantDto dto);
  ParticipantResponseDto getByEmail(String email);
}

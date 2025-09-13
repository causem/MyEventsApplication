package com.example.MyEvents.service.impl;

import com.example.MyEvents.dto.ParticipantDto;
import com.example.MyEvents.dto.ParticipantResponseDto;
import com.example.MyEvents.exception.ParticipantNotFoundException;
import com.example.MyEvents.mapper.ParticipantMapper;
import com.example.MyEvents.model.Participant;
import com.example.MyEvents.repository.ParticipantRepository;
import com.example.MyEvents.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepo;

  @Override
  public List<ParticipantResponseDto> getAll() {
    return participantRepo.findAll().stream()
            .map(ParticipantMapper::toDto)
            .toList();
  }

  @Override
  public ParticipantResponseDto getById(Long id) {
    Participant p = participantRepo.findById(id)
            .orElseThrow(() -> new ParticipantNotFoundException(id));
    return ParticipantMapper.toDto(p);
  }

  @Override
  public ParticipantResponseDto create(ParticipantDto dto) {
    Participant p = ParticipantMapper.toEntity(dto);
    Participant saved = participantRepo.save(p);
    return ParticipantMapper.toDto(saved);
  }
}
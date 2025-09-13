package com.example.MyEvents.service.impl;

import com.example.MyEvents.dto.RegistrationResponseDto;
import com.example.MyEvents.exception.*;
import com.example.MyEvents.mapper.RegistrationMapper;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.model.Participant;
import com.example.MyEvents.model.Registration;
import com.example.MyEvents.repository.EventRepository;
import com.example.MyEvents.repository.ParticipantRepository;
import com.example.MyEvents.repository.RegistrationRepository;
import com.example.MyEvents.service.EventService;
import com.example.MyEvents.service.ParticipantService;
import com.example.MyEvents.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

  private final RegistrationRepository registrationRepo;
  private final EventRepository eventRepo;
  private final ParticipantRepository participantRepo;

  @Override
  public RegistrationResponseDto register(Long eventId, Long participantId) {
    Event event = eventRepo.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));

    Participant participant = participantRepo.findById(participantId)
            .orElseThrow(() -> new ParticipantNotFoundException(participantId));

    validateRegistration(event, participant);

    Registration r = new Registration();
    r.setEvent(event);
    r.setParticipant(participant);
    r.setRegistrationDate(LocalDateTime.now());

    Registration saved = registrationRepo.save(r);
    return RegistrationMapper.toDto(saved);
  }

  @Override
  public RegistrationResponseDto registerByEmail(Long eventId, String email) {
    Event event = eventRepo.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));

    Participant participant = participantRepo.findByEmail(email)
            .orElseThrow(() -> new ParticipantNotFoundException(email));

    validateRegistration(event, participant);

    Registration r = new Registration();
    r.setEvent(event);
    r.setParticipant(participant);
    r.setRegistrationDate(LocalDateTime.now());

    Registration saved = registrationRepo.save(r);
    return RegistrationMapper.toDto(saved);
  }

  @Override
  public void cancel(Long eventId, Long participantId) {
    Registration r = registrationRepo.findByEvent_IdAndParticipant_Id(eventId, participantId)
            .orElseThrow(() -> new RegistrationNotFoundException(eventId, participantId));
    registrationRepo.delete(r);
  }


  private void validateRegistration(Event event, Participant participant) {
    Long eventId = event.getId();
    Long participantId = participant.getId();

    if (registrationRepo.existsByEvent_IdAndParticipant_Id(eventId, participantId)) {
      throw new AlreadyRegisteredException(eventId, participantId);
    }

    long taken = registrationRepo.countByEvent_Id(eventId);
    if (taken >= event.getCapacity()) {
      throw new RegistrationFullException(eventId);
    }
  }
  @Override
  @Transactional(readOnly = true)
  public List<RegistrationResponseDto> getRegistrationsForEvent(Long eventId) {
    if (!eventRepo.existsById(eventId)) {
      throw new EventNotFoundException(eventId);
    }
    List<Registration> list = registrationRepo.findAllByEventIdFetchParticipant(eventId);
    return list.stream().map(RegistrationMapper::toDto).toList();
  }
}
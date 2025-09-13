package com.example.MyEvents.service.impl;

import com.example.MyEvents.exception.AlreadyRegisteredException;
import com.example.MyEvents.exception.RegistrationFullException;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.model.Participant;
import com.example.MyEvents.model.Registration;
import com.example.MyEvents.repository.RegistrationRepository;
import com.example.MyEvents.service.EventService;
import com.example.MyEvents.service.ParticipantService;
import com.example.MyEvents.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

  private final RegistrationRepository registrationRepo;
  private final EventService eventService;
  private final ParticipantService participantService;

  @Override
  @Transactional
  public Registration register(Long eventId, Long participantId) {
    Event event = eventService.getById(eventId);
    Participant participant = participantService.getById(participantId);

    if (registrationRepo.existsByEvent_IdAndParticipant_Id(eventId, participantId)) {
      throw new AlreadyRegisteredException(eventId,participantId);
    }

    if (!eventService.hasFreeSeats(eventId)) {
      throw new RegistrationFullException(eventId);
    }

    Registration r = Registration.builder()
            .event(event)
            .participant(participant)
            .registrationDate(LocalDateTime.now())
            .build();
    return registrationRepo.save(r);
  }

  @Override
  @Transactional
  public Registration registerByEmail(Long eventId, String participantName, String email) {
    Participant p = participantService.registerOrGet(participantName, email);
    return register(eventId, p.getId());
  }

  @Override
  @Transactional
  public void cancel(Long eventId, Long participantId) {
    Registration r = registrationRepo.findByEvent_IdAndParticipant_Id(eventId, participantId)
            .orElseThrow(() -> new RuntimeException("Registration not found"));
    registrationRepo.delete(r);
  }
}
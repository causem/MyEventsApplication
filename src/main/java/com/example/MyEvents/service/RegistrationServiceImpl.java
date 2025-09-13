package com.example.MyEvents.service;

import com.example.MyEvents.exception.NotFoundException;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.model.Participant;
import com.example.MyEvents.model.Registration;
import com.example.MyEvents.repository.EventRepository;
import com.example.MyEvents.repository.ParticipantRepository;
import com.example.MyEvents.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final EventRepository eventRepo;
  private final ParticipantRepository participantRepo;
  private final RegistrationRepository regRepo;

  /**
   * Używamy OPTIMISTIC_FORCE_INCREMENT na evencie, żeby uniknąć wyścigu o ostatnie miejsca.
   * Alternatywa: PESSIMISTIC_WRITE lock.
   */
  @Override @Transactional
  public Registration registerParticipant(Long eventId, Long participantId) {
    Event event = eventRepo.findWithLockById(eventId)
        .orElseThrow(() -> new NotFoundException("Event "+eventId+" not found"));
    Participant p = participantRepo.findById(participantId)
        .orElseThrow(() -> new NotFoundException("Participant "+participantId+" not found"));

    // 1) duplikat?
    if (regRepo.existsByEventIdAndParticipantId(eventId, participantId)) {
      throw new DuplicateRegistrationException("Already registered");
    }

    // 2) limit miejsc?
    long taken = regRepo.countByEventId(eventId);
    if (taken >= event.getCapacity()) {
      throw new CapacityExceededException("No seats left");
    }

    // 3) zapis
    Registration r = new Registration();
    r.setEvent(event);
    r.setParticipant(p);
    r.setRegisteredAt(LocalDateTime.now());
    return regRepo.save(r);
  }

  @Override @Transactional(readOnly = true)
  public List<Registration> listRegistrations(Long eventId) {
    // opcjonalnie sprawdź, że event istnieje
    return regRepo.findByEventId(eventId);
  }

  @Override @Transactional
  public void cancel(Long registrationId) {
    regRepo.deleteById(registrationId);
  }
}

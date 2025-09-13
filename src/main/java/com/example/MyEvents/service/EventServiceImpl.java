package com.example.MyEvents.service;

import com.example.MyEvents.exception.NotFoundException;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.repository.EventRepository;
import com.example.MyEvents.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepo;
  private final RegistrationRepository regRepo;

  @Override @Transactional
  public Event create(Event e) { return eventRepo.save(e); }

  @Override
  public Event create(Event e) {
    return null;
  }

  @Override @Transactional(readOnly = true)
  public Event get(Long id) {
    return eventRepo.findById(id).orElseThrow(() -> new NotFoundException("Event "+id+" not found"));
  }

  @Override @Transactional
  public Event update(Long id, Consumer<Event> mutator) {
    Event e = get(id);
    mutator.accept(e);
    return e; // dirty checking
  }

  @Override @Transactional
  public void delete(Long id) { eventRepo.delete(get(id)); }

  @Override @Transactional(readOnly = true)
  public List<Event> list() { return eventRepo.findAll(Sort.by("startsAt").ascending()); }

  @Override @Transactional(readOnly = true)
  public boolean hasFreeSeats(Long eventId) { return freeSeats(eventId) > 0; }

  @Override @Transactional(readOnly = true)
  public int freeSeats(Long eventId) {
    Event e = get(eventId);
    long taken = regRepo.countByEventId(eventId);
    return e.getCapacity() - (int)taken;
  }
}

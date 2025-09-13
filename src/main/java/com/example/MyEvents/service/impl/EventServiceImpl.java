package com.example.MyEvents.service.impl;

import com.example.MyEvents.exception.EventNotFoundException;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.model.Location;
import com.example.MyEvents.repository.EventRepository;
import com.example.MyEvents.repository.LocationRepository;
import com.example.MyEvents.repository.RegistrationRepository;
import com.example.MyEvents.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
  private final EventRepository eventRepo;
  private final LocationRepository locationRepo;
  private final RegistrationRepository registrationRepo;

  @Override
  public List<Event> getAll() {
    return eventRepo.findAllWithLocation();
  }

  @Override
  public Event getById(Long id) {
    return eventRepo.findByIdWithLocation(id)
            .orElseThrow(() -> new EventNotFoundException(id));
  }

  @Override
  @Transactional
  public Event create(String name, String description, LocalDateTime date, int capacity, Long locationId) {
    Location loc = locationRepo.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Location " + locationId + " not found"));

    Event e = Event.builder()
            .name(name)
            .description(description)
            .date(date)
            .capacity(capacity)
            .location(loc)
            .build();
    return eventRepo.save(e);
  }

  @Override
  @Transactional
  public Event update(Long id, String name, String description, LocalDateTime date, Integer capacity, Long locationId) {
    Event e = getById(id);
    if (name != null) e.setName(name);
    if (description != null) e.setDescription(description);
    if (date != null) e.setDate(date);
    if (capacity != null) e.setCapacity(capacity);
    if (locationId != null) {
      Location loc = locationRepo.findById(locationId)
              .orElseThrow(() -> new RuntimeException("Location " + locationId + " not found"));
      e.setLocation(loc);
    }
    return e;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!eventRepo.existsById(id)) throw new RuntimeException("Event " + id + " not found");
    eventRepo.deleteById(id);
  }

  @Override
  public long takenSeats(Long eventId) {
    return registrationRepo.countByEvent_Id(eventId);
  }

  @Override
  public int availableSeats(Long eventId) {
    Event e = getById(eventId);
    long taken = takenSeats(eventId);
    return Math.max(0, e.getCapacity() - (int) taken);
  }

  @Override
  public boolean hasFreeSeats(Long eventId) {
    return availableSeats(eventId) > 0;
  }
}

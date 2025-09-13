package com.example.MyEvents.service.impl;

import com.example.MyEvents.dto.*;
import com.example.MyEvents.exception.EventNotFoundException;
import com.example.MyEvents.exception.LocationNotFoundException;
import com.example.MyEvents.mapper.EventMapper;
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
  public List<EventResponseDto> getAll() {
    return eventRepo.findAll().stream()
            .map(EventMapper::toDto)
            .toList();
  }

  @Override
  public EventResponseDto getById(Long id) {
    Event e = eventRepo.findById(id)
            .orElseThrow(() -> new EventNotFoundException(id));
    return EventMapper.toDto(e);
  }

  @Override
  public EventResponseDto create(EventCreateDto dto) {
    Location loc = locationRepo.findById(dto.locationId())
            .orElseThrow(() -> new LocationNotFoundException(dto.locationId()));
    Event saved = eventRepo.save(EventMapper.toEntity(dto, loc));
    return EventMapper.toDto(saved);
  }

  @Override
  public EventResponseDto update(Long id, EventUpdateDto dto) {
    Event e = eventRepo.findById(id)
            .orElseThrow(() -> new EventNotFoundException(id));

    Location loc = null;
    if (dto.locationId() != null) {
      loc = locationRepo.findById(dto.locationId())
              .orElseThrow(() -> new LocationNotFoundException(dto.locationId()));
    }

    EventMapper.apply(dto, e, loc);
    return EventMapper.toDto(e);
  }

  @Override
  public void delete(Long id) {
    if (!eventRepo.existsById(id)) {
      throw new EventNotFoundException(id);
    }
    eventRepo.deleteById(id);
  }

  @Override
  public long takenSeats(Long eventId) {
    return registrationRepo.countByEvent_Id(eventId);
  }

  @Override
  public int availableSeats(Long eventId) {
    Event e = eventRepo.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));
    long taken = registrationRepo.countByEvent_Id(eventId);
    return Math.max(0, e.getCapacity() - (int) taken);
  }

  @Override
  public boolean hasFreeSeats(Long eventId) {
    return availableSeats(eventId) > 0;
  }

  @Override
  public SeatsStatusDto seats(Long eventId) {
    Event e = eventRepo.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));
    long taken = registrationRepo.countByEvent_Id(eventId);
    return EventMapper.seatsOf(e, taken);
  }

  @Override
  public EventWithSeatsDto getByIdWithSeats(Long id) {
    Event e = eventRepo.findById(id)
            .orElseThrow(() -> new EventNotFoundException(id));
    long taken = registrationRepo.countByEvent_Id(id);
    int available = Math.max(0, e.getCapacity() - (int) taken);
    return EventMapper.toDtoWithSeats(e, taken, available);
  }
}
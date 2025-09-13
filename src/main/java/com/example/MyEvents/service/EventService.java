package com.example.MyEvents.service;


import com.example.MyEvents.dto.*;

import java.util.List;

public interface EventService {

  List<EventResponseDto> getAll();

  EventResponseDto getById(Long id);

  EventResponseDto create(EventCreateDto dto);

  EventResponseDto update(Long id, EventUpdateDto dto);

  void delete(Long id);

  long takenSeats(Long eventId);

  int availableSeats(Long eventId);

  boolean hasFreeSeats(Long eventId);

  SeatsStatusDto seats(Long eventId);

  EventWithSeatsDto getByIdWithSeats(Long id);
}
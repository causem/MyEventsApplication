package com.example.MyEvents.service;

import com.example.MyEvents.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface EventService {
  List<Event> getAll();
  Event getById(Long id);
  Event create(String name, String description, LocalDateTime date, int capacity, Long locationId);
  Event update(Long id, String name, String description, LocalDateTime date, Integer capacity, Long locationId);
  void delete(Long id);

  long takenSeats(Long eventId);
  int availableSeats(Long eventId);
  boolean hasFreeSeats(Long eventId);
}

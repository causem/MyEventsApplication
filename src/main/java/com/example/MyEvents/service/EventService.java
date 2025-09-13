package com.example.MyEvents.service;

import com.example.MyEvents.model.Event;

import java.util.List;
import java.util.function.Consumer;

public interface EventService {
  Event create(Event e);
  Event get(Long id);
  Event update(Long id, Consumer<Event> mutator);
  void delete(Long id);
  List<Event> list();               // lista wydarzeń
  boolean hasFreeSeats(Long eventId);
  int freeSeats(Long eventId);
}

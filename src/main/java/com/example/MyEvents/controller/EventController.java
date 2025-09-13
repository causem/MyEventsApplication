package com.example.MyEvents.controller;

import com.example.MyEvents.model.Event;
import com.example.MyEvents.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;


    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }
}

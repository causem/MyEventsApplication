package com.example.MyEvents.controller.rest;

import com.example.MyEvents.dto.*;
import com.example.MyEvents.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Events", description = "CRUD operations on events and seat statistics")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Get all events", description = "Returns list of all events")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    public ResponseEntity<List<EventResponseDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Returns details of a single event")
    @ApiResponse(responseCode = "200", description = "Event found and returned")
    @ApiResponse(responseCode = "404", description = "Event not found")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/{id}/with-seats")
    @Operation(summary = "Get event with seat statistics",
            description = "Returns event details together with taken and available seats")
    @ApiResponse(responseCode = "200", description = "Event with seat info returned")
    @ApiResponse(responseCode = "404", description = "Event not found")
    public ResponseEntity<EventWithSeatsDto> getByIdWithSeats(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getByIdWithSeats(id));
    }

    @GetMapping("/{id}/seats")
    @Operation(summary = "Get seats status", description = "Returns seat statistics for given event")
    @ApiResponse(responseCode = "200", description = "Seats status returned")
    @ApiResponse(responseCode = "404", description = "Event not found")
    public ResponseEntity<SeatsStatusDto> getSeats(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.seats(id));
    }

    @PostMapping
    @Operation(summary = "Create new event", description = "Creates new event with provided data")
    @ApiResponse(responseCode = "200", description = "Event created successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed")
    @ApiResponse(responseCode = "404", description = "Location not found")
    public ResponseEntity<EventResponseDto> create(@Valid @RequestBody EventCreateDto dto) {
        return ResponseEntity.ok(eventService.create(dto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update event", description = "Updates event details by ID")
    @ApiResponse(responseCode = "200", description = "Event updated successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed")
    @ApiResponse(responseCode = "404", description = "Event or Location not found")
    public ResponseEntity<EventResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody EventUpdateDto dto) {
        return ResponseEntity.ok(eventService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event", description = "Deletes event by ID")
    @ApiResponse(responseCode = "204", description = "Event deleted successfully")
    @ApiResponse(responseCode = "404", description = "Event not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

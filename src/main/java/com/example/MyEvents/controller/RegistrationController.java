package com.example.MyEvents.controller;

import com.example.MyEvents.dto.RegistrationResponseDto;
import com.example.MyEvents.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registrations", description = "Manage participant registrations for events")
@RestController
@RequestMapping("/api/events/{eventId}/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/{participantId}")
    @Operation(summary = "Register participant by ID",
            description = "Registers participant for event if seats are available")
    @ApiResponse(responseCode = "200", description = "Registered successfully")
    @ApiResponse(responseCode = "409", description = "Already registered or no seats available")
    public ResponseEntity<RegistrationResponseDto> register(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        return ResponseEntity.ok(registrationService.register(eventId, participantId));
    }

    @PostMapping("/by-email")
    @Operation(summary = "Register participant by email",
            description = "Registers participant for event using participant's email")
    @ApiResponse(responseCode = "200", description = "Registered successfully")
    @ApiResponse(responseCode = "404", description = "Event or participant not found")
    @ApiResponse(responseCode = "409", description = "Already registered or no seats available")
    public ResponseEntity<RegistrationResponseDto> registerByEmail(
            @PathVariable Long eventId,
            @RequestParam String email) {
        return ResponseEntity.ok(registrationService.registerByEmail(eventId, email));
    }

    @DeleteMapping("/{participantId}")
    @Operation(summary = "Cancel registration",
            description = "Cancels participant registration for given event")
    @ApiResponse(responseCode = "204", description = "Registration cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Registration not found")
    public ResponseEntity<Void> cancel(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        registrationService.cancel(eventId, participantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get registrations for an event",
            description = "Returns the list of participants registered for the given event")
    @ApiResponse(responseCode = "200", description = "Registrations returned")
    @ApiResponse(responseCode = "404", description = "Event not found")
    public ResponseEntity<List<RegistrationResponseDto>> getRegistrationsForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.getRegistrationsForEvent(eventId));
    }
}

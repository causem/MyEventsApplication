package com.example.MyEvents.controller;

import com.example.MyEvents.dto.ParticipantDto;
import com.example.MyEvents.dto.ParticipantResponseDto;
import com.example.MyEvents.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Participants", description = "Register and fetch participants")
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    @Operation(summary = "Create new participant", description = "Registers new participant")
    @ApiResponse(responseCode = "200", description = "Participant created successfully")
    @ApiResponse(responseCode = "400", description = "Validation failed")
    public ResponseEntity<ParticipantResponseDto> create(@Valid @RequestBody ParticipantDto dto) {
        return ResponseEntity.ok(participantService.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get participant by ID", description = "Returns details of a single participant")
    @ApiResponse(responseCode = "200", description = "Participant found and returned")
    @ApiResponse(responseCode = "404", description = "Participant not found")
    public ResponseEntity<ParticipantResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getById(id));
    }

    @GetMapping("/by-email")
    @Operation(summary = "Get participant by email", description = "Returns details of a participant by email")
    @ApiResponse(responseCode = "200", description = "Participant found and returned")
    @ApiResponse(responseCode = "404", description = "Participant not found")
    public ResponseEntity<ParticipantResponseDto> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(participantService.getByEmail(email));
    }
}

package com.example.MyEvents.service;

import com.example.MyEvents.dto.RegistrationResponseDto;
import com.example.MyEvents.model.Registration;

import java.util.List;

public interface RegistrationService {
  RegistrationResponseDto register(Long eventId, Long participantId);
  RegistrationResponseDto registerByEmail(Long eventId, String email);
  void cancel(Long eventId, Long participantId);
}

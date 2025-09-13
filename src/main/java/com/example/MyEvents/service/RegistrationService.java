package com.example.MyEvents.service;

import com.example.MyEvents.model.Registration;

import java.util.List;

public interface RegistrationService {
  Registration register(Long eventId, Long participantId);
  Registration registerByEmail(Long eventId, String participantName, String email);
  void cancel(Long eventId, Long participantId);
}

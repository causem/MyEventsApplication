package com.example.MyEvents.service;

import com.example.MyEvents.model.Registration;

import java.util.List;

public interface RegistrationService {
  Registration registerParticipant(Long eventId, Long participantId);
  List<Registration> listRegistrations(Long eventId);
  void cancel(Long registrationId);
}

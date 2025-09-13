package com.example.MyEvents.service;

import com.example.MyEvents.model.Participant;

public interface ParticipantService {
  Participant register(String name, String email);
  Participant get(Long id);
}

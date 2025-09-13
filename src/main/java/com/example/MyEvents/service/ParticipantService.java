package com.example.MyEvents.service;

import com.example.MyEvents.model.Participant;

public interface ParticipantService {
  Participant registerOrGet(String name, String email);
  Participant getById(Long id);
}

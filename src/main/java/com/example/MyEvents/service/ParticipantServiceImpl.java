package com.example.MyEvents.service;

import com.example.MyEvents.exception.NotFoundException;
import com.example.MyEvents.model.Participant;
import com.example.MyEvents.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final ParticipantRepository repo;

  @Override @Transactional
  public Participant register(String name, String email) {
    return repo.findByEmail(email)
      .orElseGet(() -> repo.save(new Participant(/*set name/email*/)));
  }

  @Override @Transactional(readOnly = true)
  public Participant get(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Participant "+id+" not found"));
  }
}

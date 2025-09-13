package com.example.MyEvents.service.impl;

import com.example.MyEvents.model.Participant;
import com.example.MyEvents.repository.ParticipantRepository;
import com.example.MyEvents.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepo;

  @Override
  @Transactional
  public Participant registerOrGet(String name, String email) {
    return participantRepo.findByEmail(email)
            .map(p -> {
              if (name != null && !name.equals(p.getName())) {
                p.setName(name);
              }
              return p;
            })
            .orElseGet(() -> participantRepo.save(
                    Participant.builder().name(name).email(email).build()
            ));
  }

  @Override
  public Participant getById(Long id) {
    return participantRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Participant " + id + " not found"));
  }
}
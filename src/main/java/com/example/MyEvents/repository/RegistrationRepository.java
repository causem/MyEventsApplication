package com.example.MyEvents.repository;

import com.example.MyEvents.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    long countByEvent_Id(Long eventId);
    boolean existsByEvent_IdAndParticipant_Id(Long eventId, Long participantId);
    Optional<Registration> findByEvent_IdAndParticipant_Id(Long eventId, Long participantId);
}
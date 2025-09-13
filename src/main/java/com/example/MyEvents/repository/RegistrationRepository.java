package com.example.MyEvents.repository;

import com.example.MyEvents.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    long countByEvent_Id(Long eventId);
    boolean existsByEvent_IdAndParticipant_Id(Long eventId, Long participantId);
    Optional<Registration> findByEvent_IdAndParticipant_Id(Long eventId, Long participantId);
    @Query("""
        select r
        from Registration r
        join fetch r.participant p
        where r.event.id = :eventId
    """)
    List<Registration> findAllByEventIdFetchParticipant(@Param("eventId") Long eventId);
}
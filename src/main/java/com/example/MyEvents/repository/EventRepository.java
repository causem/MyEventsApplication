package com.example.MyEvents.repository;

import com.example.MyEvents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e join fetch e.location")
    List<Event> findAllWithLocation();

    @Query("select e from Event e join fetch e.location where e.id = :id")
    Optional<Event> findByIdWithLocation(@Param("id") Long id);

    List<Event> findAllByDateAfterOrderByDateAsc(LocalDateTime after);
}
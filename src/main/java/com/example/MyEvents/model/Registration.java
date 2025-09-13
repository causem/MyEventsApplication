package com.example.MyEvents.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "registrations",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"event_id","participant_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}

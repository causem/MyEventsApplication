package com.example.MyEvents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable=false)
    private LocalDateTime date;

    @Min(1)
    @Column(nullable=false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Registration> registrations = new HashSet<>();



}

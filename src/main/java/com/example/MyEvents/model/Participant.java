package com.example.MyEvents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Registration> registrations = new HashSet<>();
}

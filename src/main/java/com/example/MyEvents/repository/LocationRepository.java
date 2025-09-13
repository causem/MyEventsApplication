package com.example.MyEvents.repository;

import com.example.MyEvents.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
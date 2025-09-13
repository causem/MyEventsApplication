package com.example.MyEvents.service;

import com.example.MyEvents.dto.LocationDto;
import com.example.MyEvents.dto.LocationResponseDto;

import java.util.List;

public interface LocationService {
    List<LocationResponseDto> getAll();
    LocationResponseDto getById(Long id);
    LocationResponseDto create(LocationDto dto);
    LocationResponseDto update(Long id, LocationDto dto);
    void delete(Long id);
}

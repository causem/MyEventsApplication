package com.example.MyEvents.service.impl;

import com.example.MyEvents.dto.LocationDto;
import com.example.MyEvents.dto.LocationResponseDto;
import com.example.MyEvents.exception.LocationNotFoundException;
import com.example.MyEvents.mapper.LocationMapper;
import com.example.MyEvents.model.Location;
import com.example.MyEvents.repository.LocationRepository;
import com.example.MyEvents.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LocationResponseDto> getAll() {
        return locationRepository.findAll().stream()
                .map(LocationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LocationResponseDto getById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        return LocationMapper.toDto(location);
    }

    @Override
    public LocationResponseDto create(LocationDto dto) {
        Location location = LocationMapper.toEntity(dto);
        Location saved = locationRepository.save(location);
        return LocationMapper.toDto(saved);
    }

    @Override
    public LocationResponseDto update(Long id, LocationDto dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        LocationMapper.updateEntity(location, dto);
        return LocationMapper.toDto(location);
    }

    @Override
    public void delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        locationRepository.delete(location);
    }
}
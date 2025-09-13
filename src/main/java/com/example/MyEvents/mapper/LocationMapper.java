package com.example.MyEvents.mapper;

import com.example.MyEvents.dto.LocationDto;
import com.example.MyEvents.dto.LocationResponseDto;
import com.example.MyEvents.model.Location;

public class LocationMapper {

    public static Location toEntity(LocationDto dto) {
        Location l = new Location();
        l.setName(dto.name());
        l.setCity(dto.city());
        l.setAddress(dto.address());
        return l;
    }

    public static void updateEntity(Location l, LocationDto dto) {
        l.setName(dto.name());
        l.setCity(dto.city());
        l.setAddress(dto.address());
    }

    public static LocationResponseDto toDto(Location l) {
        return new LocationResponseDto(l.getId(), l.getName(), l.getCity(), l.getAddress());
    }
}
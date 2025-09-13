package com.example.MyEvents.mapper;

import com.example.MyEvents.dto.EventCreateDto;
import com.example.MyEvents.dto.EventFormDto;
import com.example.MyEvents.dto.LocationDto;

public class EventFormMapper {
    public static EventCreateDto toCreateDto(EventFormDto form) {
        LocationDto locDto = null;
        if (form.getLocation() != null
                && form.getLocation().getName() != null
                && !form.getLocation().getName().isBlank()) {
            locDto = new LocationDto(
                    form.getLocation().getName(),
                    form.getLocation().getCity(),
                    form.getLocation().getAddress()
            );
        }

        return new EventCreateDto(
                form.getName(),
                form.getDescription(),
                form.getDate(),
                form.getCapacity(),
                form.getLocationId(),
                locDto
        );
    }
}
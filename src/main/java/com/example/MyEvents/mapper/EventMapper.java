package com.example.MyEvents.mapper;

import com.example.MyEvents.dto.*;
import com.example.MyEvents.model.Event;
import com.example.MyEvents.model.Location;

public class EventMapper {

    public static Event toEntity(EventCreateDto dto, Location location) {
        Event e = new Event();
        e.setName(dto.name());
        e.setDescription(dto.description());
        e.setDate(dto.date());
        e.setCapacity(dto.capacity());
        e.setLocation(location);
        return e;
    }

    public static void apply(EventUpdateDto dto, Event e, Location newLocOrNull) {
        if (dto.name() != null) e.setName(dto.name());
        if (dto.description() != null) e.setDescription(dto.description());
        if (dto.date() != null) e.setDate(dto.date());
        if (dto.capacity() != null) e.setCapacity(dto.capacity());
        if (newLocOrNull != null) e.setLocation(newLocOrNull);
    }

    public static EventResponseDto toDto(Event e) {
        return new EventResponseDto(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getDate(),
                e.getCapacity(),
                e.getLocation() != null ? e.getLocation().getId() : null,
                e.getLocation() != null ? e.getLocation().getName() : null,
                e.getLocation() != null ? e.getLocation().getCity() : null,
                e.getLocation() != null ? e.getLocation().getAddress() : null
        );
    }

    public static EventWithSeatsDto toDtoWithSeats(Event e, long taken, int available) {
        return new EventWithSeatsDto(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getDate(),
                e.getCapacity(),
                e.getLocation() != null ? e.getLocation().getId() : null,
                e.getLocation() != null ? e.getLocation().getName() : null,
                e.getLocation() != null ? e.getLocation().getCity() : null,
                e.getLocation() != null ? e.getLocation().getAddress() : null,
                taken,
                available,
                available > 0
        );
    }

    public static SeatsStatusDto seatsOf(Event e, long taken) {
        int available = Math.max(0, e.getCapacity() - (int) taken);
        return new SeatsStatusDto(
                e.getId(),
                e.getCapacity(),
                taken,
                available,
                available > 0
        );
    }
}
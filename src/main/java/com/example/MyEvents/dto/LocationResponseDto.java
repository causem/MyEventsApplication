package com.example.MyEvents.dto;

public record LocationResponseDto(
        Long id,
        String name,
        String city,
        String address
) {}
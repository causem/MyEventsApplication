package com.example.MyEvents.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EventFormDto {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @Min(1)
    private int capacity = 1;

    private Long locationId;

    private NewLocationForm location;

    @Data
    public static class NewLocationForm {
        @NotBlank
        private String name;

        @NotBlank
        private String city;

        @NotBlank
        private String address;
    }
}
package com.example.MyEvents.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "MyEvents API",
        version = "1.0",
        description = "API for managing events, participants, and registrations")
)
public class OpenApiConfig {
}
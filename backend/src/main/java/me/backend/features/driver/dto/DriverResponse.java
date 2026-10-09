package me.backend.features.driver.dto;

import me.backend.features.driver.Driver;

import java.time.LocalDate;

public record DriverResponse(
        Long id,
        String firstName,
        String lastName,
        String nationality,
        LocalDate dateOfBirth,
        Integer number,
        Driver.Status status
) {
}

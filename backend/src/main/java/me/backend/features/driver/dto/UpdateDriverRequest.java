package me.backend.features.driver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.backend.features.driver.Driver;

import java.time.LocalDate;

public record UpdateDriverRequest(
        @NotBlank
        @Size(max = 100)
        String firstName,

        @NotBlank
        @Size(max = 100)
        String lastName,

        @NotBlank
        @Size(max = 100)
        String nationality,

        LocalDate dateOfBirth,

        @NotNull
        Driver.Status status
) {
}

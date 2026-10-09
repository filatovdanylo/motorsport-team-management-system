package me.backend.features.driver.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.backend.features.driver.Driver;

import java.time.LocalDate;

public record CreateDriverRequest(
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
        @Min(0)
        @Max(999)
        Integer number,

        @NotNull
        Driver.Status status
) {
}

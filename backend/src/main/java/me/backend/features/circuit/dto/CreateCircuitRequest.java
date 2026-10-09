package me.backend.features.circuit.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateCircuitRequest(
        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank
        @Size(max = 100)
        String country,

        @NotBlank
        @Size(max = 100)
        String city,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer = 4, fraction = 3)
        BigDecimal lengthKm,

        @NotNull
        @Min(1)
        Integer numberOfLaps
) {
}

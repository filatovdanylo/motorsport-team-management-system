package me.backend.features.circuit.dto;

import jakarta.validation.constraints.*;

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
        @Positive
        Integer numberOfLaps
) {
}

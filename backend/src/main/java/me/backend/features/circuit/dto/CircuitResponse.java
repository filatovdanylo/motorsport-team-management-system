package me.backend.features.circuit.dto;

import java.math.BigDecimal;

public record CircuitResponse(
        Long id,
        String name,
        String country,
        String city,
        BigDecimal lengthKm,
        Integer numberOfLaps
) {
}

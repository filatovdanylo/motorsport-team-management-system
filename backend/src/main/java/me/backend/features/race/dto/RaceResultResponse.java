package me.backend.features.race.dto;

import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.race.RaceResult;

import java.math.BigDecimal;

public record RaceResultResponse(
        Long id,
        Integer position,
        BigDecimal points,
        boolean fastestLap,
        RaceResult.Status status,
        Long totalRaceTimeMs,
        RaceResponse race,
        DriverResponse driver
) {
}

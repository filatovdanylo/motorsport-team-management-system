package me.backend.features.race.dto;

import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.race.RaceResult;

import java.math.BigDecimal;

public record RaceResultResponse(
        long id,
        int position,
        BigDecimal points,
        boolean fastestLap,
        RaceResult.Status status,
        long totalRaceTimeMs,
        RaceResponse race,
        DriverResponse driver
) {
}

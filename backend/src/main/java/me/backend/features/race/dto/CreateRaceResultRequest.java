package me.backend.features.race.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.backend.features.race.RaceResult;

import java.math.BigDecimal;

public record CreateRaceResultRequest(
        @NotNull
        @Positive
        int position,

        @NotNull
        @Positive
        BigDecimal points,

        @NotNull
        boolean fastestLap,

        @NotNull
        RaceResult.Status status,

        @NotNull
        @Positive
        long totalRaceTimeMs,

        @NotNull
        @Positive
        long driverId,

        @NotNull
        @Positive
        long raceId
) {
}

package me.backend.features.race.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import me.backend.features.race.RaceResult;

import java.math.BigDecimal;

public record CreateRaceResultRequest(
        @Positive
        Integer position,

        @NotNull
        @PositiveOrZero
        @Digits(integer = 4, fraction = 2)
        BigDecimal points,

        @NotNull
        Boolean fastestLap,

        @NotNull
        RaceResult.Status status,

        @Positive
        Long totalRaceTimeMs,

        @NotNull
        @Positive
        Long driverId,

        @NotNull
        @Positive
        Long raceId
) {
}

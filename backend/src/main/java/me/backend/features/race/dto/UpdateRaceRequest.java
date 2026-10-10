package me.backend.features.race.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import me.backend.features.race.Race;

import java.time.LocalDate;

public record UpdateRaceRequest(
        @NotBlank
        @Size(max = 150)
        String name,

        @NotNull
        @Positive
        Integer roundNumber,

        @NotNull
        LocalDate raceDate,

        @NotNull
        Race.Status status,

        @NotNull
        @Positive
        Long seasonId,

        @NotNull
        @Positive
        Long circuitId
) {
}

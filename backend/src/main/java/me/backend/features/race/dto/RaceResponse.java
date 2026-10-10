package me.backend.features.race.dto;

import me.backend.features.circuit.dto.CircuitResponse;
import me.backend.features.race.Race;
import me.backend.features.season.dto.SeasonResponse;

import java.time.LocalDate;

public record RaceResponse(
        Long id,
        String name,
        int roundNumber,
        LocalDate raceDate,
        Race.Status status,
        SeasonResponse season,
        CircuitResponse circuit
) {
}

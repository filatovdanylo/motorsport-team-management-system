package me.backend.features.team.dto;

public record TeamResponse(
        Long id,
        String name,
        String shortName,
        String country,
        Integer foundedYear,
        String teamPrincipal
) {
}

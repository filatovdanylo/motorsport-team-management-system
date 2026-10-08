package me.backend.features.team.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTeamRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 100)
        String country,

        @NotNull
        @Min(1800)
        @Max(2100)
        Integer foundedYear,

        @NotBlank
        @Size(max = 150)
        String teamPrincipal
) {
}

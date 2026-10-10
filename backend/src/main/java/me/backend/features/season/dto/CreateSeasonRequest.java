package me.backend.features.season.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.backend.features.season.Season;

public record CreateSeasonRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @Min(1900)
        @Max(2200)
        Integer year,

        @NotNull
        Season.Status status
) {
}

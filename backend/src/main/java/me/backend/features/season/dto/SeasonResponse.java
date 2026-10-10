package me.backend.features.season.dto;

import me.backend.features.season.Season;

public record SeasonResponse(
        Long id,
        String name,
        int year,
        Season.Status status
) {
}

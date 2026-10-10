package me.backend.features.season.service;

import me.backend.exception.ResourceNotFoundException;
import me.backend.features.season.Season;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.season.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Transactional(readOnly = true)
    public SeasonResponse getSeasonById(long id) {
        return seasonRepository.findById(id)
                .map(SeasonService::mapSeasonToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id " + id));
    }

    private static SeasonResponse mapSeasonToDto(Season season) {
        return new SeasonResponse(
                season.getId(),
                season.getName(),
                season.getYear(),
                season.getStatus()
        );
    }
}

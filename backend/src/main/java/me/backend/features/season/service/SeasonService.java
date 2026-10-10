package me.backend.features.season.service;

import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.season.Season;
import me.backend.features.season.dto.CreateSeasonRequest;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.season.dto.UpdateSeasonRequest;
import me.backend.features.season.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Transactional(readOnly = true)
    public List<SeasonResponse> getAllSeasons() {
        return seasonRepository.findAll().stream()
                .map(SeasonService::mapSeasonToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SeasonResponse getSeasonById(long id) {
        return seasonRepository.findById(id)
                .map(SeasonService::mapSeasonToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id " + id));
    }

    @Transactional
    public SeasonResponse createSeason(CreateSeasonRequest request) {
        if (seasonRepository.existsByName(request.name())) {
            throw new AlreadyExistsException("Season with name '" + request.name() + "' already exists");
        }
        if (seasonRepository.existsByYear(request.year())) {
            throw new AlreadyExistsException("Season with year '" + request.year() + "' already exists");
        }

        var newSeason = new Season(
                request.name(),
                request.year(),
                request.status(),
                OffsetDateTime.now()
        );

        seasonRepository.save(newSeason);

        return mapSeasonToDto(newSeason);
    }

    @Transactional
    public SeasonResponse updateSeason(long id, UpdateSeasonRequest request) {
        Season databaseSeason = seasonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Season with id " + id + " not found"));

        if (seasonRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new AlreadyExistsException("Season with name '" + request.name() + "' already exists");
        }
        if (seasonRepository.existsByYearAndIdNot(request.year(), id)) {
            throw new AlreadyExistsException("Season with year '" + request.year() + "' already exists");
        }

        databaseSeason.setName(request.name());
        databaseSeason.setYear(request.year());
        databaseSeason.setStatus(request.status());

        seasonRepository.save(databaseSeason);

        return mapSeasonToDto(databaseSeason);
    }

    @Transactional
    public void deleteSeasonById(long id) {
        if (!seasonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Season with id " + id + " not found");
        }

        seasonRepository.deleteById(id);
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

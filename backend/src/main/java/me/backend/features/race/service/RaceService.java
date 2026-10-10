package me.backend.features.race.service;

import jakarta.persistence.EntityManager;
import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.circuit.Circuit;
import me.backend.features.circuit.dto.CircuitResponse;
import me.backend.features.circuit.service.CircuitService;
import me.backend.features.race.Race;
import me.backend.features.race.dto.CreateRaceRequest;
import me.backend.features.race.dto.RaceResponse;
import me.backend.features.race.dto.UpdateRaceRequest;
import me.backend.features.race.repository.RaceRepository;
import me.backend.features.season.Season;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.season.service.SeasonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RaceService {
    private final RaceRepository raceRepository;
    private final CircuitService circuitService;
    private final SeasonService seasonService;
    private final EntityManager entityManager;

    public RaceService(
            RaceRepository raceRepository,
            CircuitService circuitService,
            SeasonService seasonService,
            EntityManager entityManager
    ) {
        this.raceRepository = raceRepository;
        this.circuitService = circuitService;
        this.seasonService = seasonService;
        this.entityManager = entityManager;
    }

    public List<RaceResponse> findAll() {
        return raceRepository.findAllWithDetails().stream()
                .map(RaceService::mapRaceToDto)
                .toList();
    }

    public RaceResponse findById(long id) {
        return raceRepository.findByIdWithDetails(id)
                .map(RaceService::mapRaceToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Race not found with id: " + id));
    }

    @Transactional
    public void delete(long id) {
        if (!raceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Race not found with id: " + id);
        }

        raceRepository.deleteById(id);
    }

    @Transactional
    public RaceResponse create(CreateRaceRequest request) {
        SeasonResponse seasonDto = seasonService.getSeasonById(request.seasonId());
        CircuitResponse circuitDto = circuitService.getCircuitById(request.circuitId());

        if (raceRepository.existsBySeasonIdAndRoundNumber(request.seasonId(), request.roundNumber())) {
            throw new AlreadyExistsException(
                    "Race with round number " + request.roundNumber()
                            + " already exists in season " + request.seasonId()
            );
        }

        var race = new Race(
                request.name(),
                request.roundNumber(),
                request.raceDate(),
                Race.Status.SCHEDULED
        );
        race.setSeason(entityManager.getReference(Season.class, request.seasonId()));
        race.setCircuit(entityManager.getReference(Circuit.class, request.circuitId()));

        raceRepository.save(race);

        return mapRaceToDto(race, seasonDto, circuitDto);
    }

    @Transactional
    public RaceResponse update(long id, UpdateRaceRequest request) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race not found with id: " + id));

        SeasonResponse seasonDto = seasonService.getSeasonById(request.seasonId());
        CircuitResponse circuitDto = circuitService.getCircuitById(request.circuitId());

        if (raceRepository.existsBySeasonIdAndRoundNumberAndIdNot(
                request.seasonId(),
                request.roundNumber(),
                id
        )) {
            throw new AlreadyExistsException(
                    "Race with round number " + request.roundNumber()
                            + " already exists in season " + request.seasonId()
            );
        }

        race.setName(request.name());
        race.setRoundNumber(request.roundNumber());
        race.setRaceDate(request.raceDate());
        race.setStatus(request.status());
        race.setSeason(entityManager.getReference(Season.class, request.seasonId()));
        race.setCircuit(entityManager.getReference(Circuit.class, request.circuitId()));

        raceRepository.save(race);

        return mapRaceToDto(race, seasonDto, circuitDto);
    }

    private static RaceResponse mapRaceToDto(Race race) {
        return mapRaceToDto(
                race,
                new SeasonResponse(
                        race.getSeason().getId(),
                        race.getSeason().getName(),
                        race.getSeason().getYear(),
                        race.getSeason().getStatus()
                ),
                new CircuitResponse(
                        race.getCircuit().getId(),
                        race.getCircuit().getName(),
                        race.getCircuit().getCountry(),
                        race.getCircuit().getCity(),
                        race.getCircuit().getLengthKm(),
                        race.getCircuit().getNumberOfLaps()
                )
        );
    }

    private static RaceResponse mapRaceToDto(Race race, SeasonResponse seasonDto, CircuitResponse circuitDto) {
        return new RaceResponse(
                race.getId(),
                race.getName(),
                race.getRoundNumber(),
                race.getRaceDate(),
                race.getStatus(),
                seasonDto,
                circuitDto
        );
    }
}

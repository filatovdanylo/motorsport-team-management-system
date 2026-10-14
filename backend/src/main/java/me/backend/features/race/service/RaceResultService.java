package me.backend.features.race.service;

import jakarta.persistence.EntityManager;
import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.circuit.dto.CircuitResponse;
import me.backend.features.driver.Driver;
import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.driver.service.DriverService;
import me.backend.features.race.Race;
import me.backend.features.race.RaceResult;
import me.backend.features.race.dto.CreateRaceResultRequest;
import me.backend.features.race.dto.RaceResponse;
import me.backend.features.race.dto.RaceResultResponse;
import me.backend.features.race.dto.UpdateRaceResultRequest;
import me.backend.features.race.repository.RaceResultRepository;
import me.backend.features.season.dto.SeasonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RaceResultService {
    private final RaceResultRepository raceResultRepository;
    private final RaceService raceService;
    private final DriverService driverService;
    private final EntityManager entityManager;

    public RaceResultService(
            RaceResultRepository raceResultRepository,
            RaceService raceService,
            DriverService driverService,
            EntityManager entityManager
    ) {
        this.raceResultRepository = raceResultRepository;
        this.raceService = raceService;
        this.driverService = driverService;
        this.entityManager = entityManager;
    }

    public List<RaceResultResponse> getAll(Long driverId) {
        List<RaceResult> results = driverId == null
                ? raceResultRepository.getAllWithDetails()
                : raceResultRepository.findAllByDriverIdWithDetails(driverId);

        return results.stream()
                .map(RaceResultService::mapRaceResultToDto)
                .toList();
    }

    public RaceResultResponse getById(long id) {
        return raceResultRepository.findByIdWithDetails(id)
                .map(RaceResultService::mapRaceResultToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Race result with id " + id + " not found"));
    }

    @Transactional
    public RaceResultResponse create(CreateRaceResultRequest request) {
        var raceDto = raceService.findById(request.raceId());
        var driverDto = driverService.getDriverById(request.driverId());

        if (raceResultRepository.existsByDriverIdAndRaceId(request.driverId(), request.raceId())) {
            throw driverAlreadyHasResult(request.driverId(), request.raceId());
        }

        if (request.position() != null
                && raceResultRepository.existsByRaceIdAndPosition(request.raceId(), request.position())) {
            throw positionAlreadyTaken(request.raceId(), request.position());
        }

        var newRaceResult = new RaceResult(
                request.position(),
                request.points(),
                request.fastestLap(),
                request.status(),
                request.totalRaceTimeMs()
        );
        newRaceResult.setRace(entityManager.getReference(Race.class, request.raceId()));
        newRaceResult.setDriver(entityManager.getReference(Driver.class, request.driverId()));

        raceResultRepository.save(newRaceResult);

        return mapRaceResultToDto(newRaceResult, raceDto, driverDto);
    }

    @Transactional
    public void delete(long id) {
        if (!raceResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("Race result with id " + id + " not found");
        }

        raceResultRepository.deleteById(id);
    }

    @Transactional
    public RaceResultResponse update(long id, UpdateRaceResultRequest request) {
        var raceResult = raceResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race result with id " + id + " not found"));

        var raceDto = raceService.findById(request.raceId());
        var driverDto = driverService.getDriverById(request.driverId());

        if (raceResultRepository.existsByDriverIdAndRaceIdAndIdNot(request.driverId(), request.raceId(), id)) {
            throw driverAlreadyHasResult(request.driverId(), request.raceId());
        }
        if (request.position() != null
                && raceResultRepository.existsByRaceIdAndPositionAndIdNot(request.raceId(), request.position(), id)) {
            throw positionAlreadyTaken(request.raceId(), request.position());
        }

        raceResult.setPosition(request.position());
        raceResult.setPoints(request.points());
        raceResult.setFastestLap(request.fastestLap());
        raceResult.setStatus(request.status());
        raceResult.setTotalRaceTimeMs(request.totalRaceTimeMs());
        raceResult.setRace(entityManager.getReference(Race.class, request.raceId()));
        raceResult.setDriver(entityManager.getReference(Driver.class, request.driverId()));

        raceResultRepository.save(raceResult);

        return mapRaceResultToDto(raceResult, raceDto, driverDto);
    }

    private static AlreadyExistsException driverAlreadyHasResult(Long driverId, Long raceId) {
        return new AlreadyExistsException(
                "Race result for driver with id " + driverId + " in race with id " + raceId + " already exists");
    }

    private static AlreadyExistsException positionAlreadyTaken(Long raceId, Integer position) {
        return new AlreadyExistsException(
                "Position " + position + " is already taken in race with id " + raceId);
    }

    private static RaceResultResponse mapRaceResultToDto(RaceResult raceResult, RaceResponse raceDto, DriverResponse driverDto) {
        return new RaceResultResponse(
                raceResult.getId(),
                raceResult.getPosition(),
                raceResult.getPoints(),
                raceResult.isFastestLap(),
                raceResult.getStatus(),
                raceResult.getTotalRaceTimeMs(),
                raceDto,
                driverDto
        );
    }

    private static RaceResultResponse mapRaceResultToDto(RaceResult raceResult) {
        Race race = raceResult.getRace();
        Driver driver = raceResult.getDriver();

        return mapRaceResultToDto(
                raceResult,
                new RaceResponse(
                        race.getId(),
                        race.getName(),
                        race.getRoundNumber(),
                        race.getRaceDate(),
                        race.getStatus(),
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
                ),
                new DriverResponse(
                        driver.getId(),
                        driver.getFirstName(),
                        driver.getLastName(),
                        driver.getNationality(),
                        driver.getDateOfBirth(),
                        driver.getNumber(),
                        driver.getStatus()
                )
        );
    }
}

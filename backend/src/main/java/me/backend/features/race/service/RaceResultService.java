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
import org.springframework.data.repository.query.Param;
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

    public List<RaceResultResponse> getAll() {
        return raceResultRepository.getAllWithDetails()
                .stream().map(RaceResultService::mapRaceResultToDto)
                .toList();
    }

    public RaceResultResponse getById(@Param("id") Long id) {
        return raceResultRepository.findByIdWithDetails(id).map(RaceResultService::mapRaceResultToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Race result with id " + id + " not found"));
    }

    @Transactional
    public RaceResultResponse create(CreateRaceResultRequest request) {
        if (raceResultRepository.existsByDriverIdAndRaceId(request.driverId(), request.raceId())) {
            throw new AlreadyExistsException("RaceResult for driver with id "
                    + request.driverId() + " participated in the race with id " + request.raceId() + " already exists");
        }

        var raceDto = raceService.findById(request.raceId());
        var driverDto = driverService.getDriverById(request.driverId());

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


        if (raceResultRepository.existsByDriverIdAndRaceIdAndIdNot(request.driverId(), request.raceId(), id)) {
            throw new AlreadyExistsException("RaceResult for driver with id "
                    + request.driverId() + " participated in the race with id " + request.raceId() + " already exists");
        }

        var raceDto = raceService.findById(request.raceId());
        var driverDto = driverService.getDriverById(request.driverId());

        raceResult.setPosition(request.position());
        raceResult.setPoints(request.points());
        raceResult.setFastestLap(request.fastestLap());
        raceResult.setStatus(request.status());
        raceResult.setRace(entityManager.getReference(Race.class, request.raceId()));
        raceResult.setDriver(entityManager.getReference(Driver.class, request.driverId()));

        raceResultRepository.save(raceResult);

        return mapRaceResultToDto(raceResult, raceDto, driverDto);
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
        return new RaceResultResponse(
                raceResult.getId(),
                raceResult.getPosition(),
                raceResult.getPoints(),
                raceResult.isFastestLap(),
                raceResult.getStatus(),
                raceResult.getTotalRaceTimeMs(),
                new RaceResponse(
                        raceResult.getRace().getId(),
                        raceResult.getRace().getName(),
                        raceResult.getRace().getRoundNumber(),
                        raceResult.getRace().getRaceDate(),
                        raceResult.getRace().getStatus(),
                        new SeasonResponse(
                                raceResult.getRace().getSeason().getId(),
                                raceResult.getRace().getSeason().getName(),
                                raceResult.getRace().getSeason().getYear(),
                                raceResult.getRace().getSeason().getStatus()
                                ),
                        new CircuitResponse(
                                raceResult.getRace().getCircuit().getId(),
                                raceResult.getRace().getCircuit().getName(),
                                raceResult.getRace().getCircuit().getCountry(),
                                raceResult.getRace().getCircuit().getCity(),
                                raceResult.getRace().getCircuit().getLengthKm(),
                                raceResult.getRace().getCircuit().getNumberOfLaps()
                        )
                ),
                new DriverResponse(
                        raceResult.getDriver().getId(),
                        raceResult.getDriver().getFirstName(),
                        raceResult.getDriver().getLastName(),
                        raceResult.getDriver().getNationality(),
                        raceResult.getDriver().getDateOfBirth(),
                        raceResult.getDriver().getNumber(),
                        raceResult.getDriver().getStatus()
                )
        );
    }

}

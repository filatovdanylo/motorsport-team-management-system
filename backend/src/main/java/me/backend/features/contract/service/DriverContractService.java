package me.backend.features.contract.service;

import jakarta.persistence.EntityManager;
import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.contract.DriverContract;
import me.backend.features.contract.dto.CreateDriverContractRequest;
import me.backend.features.contract.dto.DriverContractResponse;
import me.backend.features.contract.dto.UpdateDriverContractRequest;
import me.backend.features.contract.repository.DriverContractRepository;
import me.backend.features.driver.Driver;
import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.driver.service.DriverService;
import me.backend.features.season.Season;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.season.service.SeasonService;
import me.backend.features.team.Team;
import me.backend.features.team.dto.TeamResponse;
import me.backend.features.team.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DriverContractService {
    private final DriverContractRepository driverContractRepository;
    private final DriverService driverService;
    private final TeamService teamService;
    private final SeasonService seasonService;
    private final EntityManager entityManager;

    public DriverContractService(
            DriverContractRepository driverContractRepository,
            DriverService driverService,
            TeamService teamService,
            SeasonService seasonService,
            EntityManager entityManager
    ) {
        this.driverContractRepository = driverContractRepository;
        this.driverService = driverService;
        this.teamService = teamService;
        this.seasonService = seasonService;
        this.entityManager = entityManager;
    }

    public List<DriverContractResponse> getAll(Long driverId) {
        List<DriverContract> contracts = driverId == null
                ? driverContractRepository.findAllWithDetails()
                : driverContractRepository.findAllByDriverIdWithDetails(driverId);

        return contracts.stream()
                .map(DriverContractService::mapContractToDto)
                .toList();
    }

    public DriverContractResponse getById(long id) {
        return driverContractRepository.findByIdWithDetails(id)
                .map(DriverContractService::mapContractToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Driver contract with id " + id + " not found"));
    }

    @Transactional
    public DriverContractResponse create(CreateDriverContractRequest request) {
        var driverDto = driverService.getDriverById(request.driverId());
        var teamDto = teamService.getTeamById(request.teamId());
        var seasonDto = seasonService.getSeasonById(request.seasonId());

        if (driverContractRepository.existsByDriverIdAndTeamIdAndSeasonId(
                request.driverId(), request.teamId(), request.seasonId())) {
            throw contractAlreadyExists(request.driverId(), request.teamId(), request.seasonId());
        }

        var newContract = new DriverContract(
                request.driverRole(),
                request.contractStart(),
                request.contractEnd(),
                request.salary(),
                request.status()
        );
        newContract.setDriver(entityManager.getReference(Driver.class, request.driverId()));
        newContract.setTeam(entityManager.getReference(Team.class, request.teamId()));
        newContract.setSeason(entityManager.getReference(Season.class, request.seasonId()));

        driverContractRepository.save(newContract);

        return mapContractToDto(newContract, driverDto, teamDto, seasonDto);
    }

    @Transactional
    public DriverContractResponse update(long id, UpdateDriverContractRequest request) {
        var contract = driverContractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver contract with id " + id + " not found"));

        var driverDto = driverService.getDriverById(request.driverId());
        var teamDto = teamService.getTeamById(request.teamId());
        var seasonDto = seasonService.getSeasonById(request.seasonId());

        if (driverContractRepository.existsByDriverIdAndTeamIdAndSeasonIdAndIdNot(
                request.driverId(), request.teamId(), request.seasonId(), id)) {
            throw contractAlreadyExists(request.driverId(), request.teamId(), request.seasonId());
        }

        contract.setDriverRole(request.driverRole());
        contract.setContractStart(request.contractStart());
        contract.setContractEnd(request.contractEnd());
        contract.setSalary(request.salary());
        contract.setStatus(request.status());
        contract.setDriver(entityManager.getReference(Driver.class, request.driverId()));
        contract.setTeam(entityManager.getReference(Team.class, request.teamId()));
        contract.setSeason(entityManager.getReference(Season.class, request.seasonId()));

        driverContractRepository.save(contract);

        return mapContractToDto(contract, driverDto, teamDto, seasonDto);
    }

    @Transactional
    public void delete(long id) {
        if (!driverContractRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver contract with id " + id + " not found");
        }

        driverContractRepository.deleteById(id);
    }

    private static AlreadyExistsException contractAlreadyExists(Long driverId, Long teamId, Long seasonId) {
        return new AlreadyExistsException("Contract for driver with id " + driverId
                + " and team with id " + teamId + " in season with id " + seasonId + " already exists");
    }

    private static DriverContractResponse mapContractToDto(
            DriverContract contract,
            DriverResponse driverDto,
            TeamResponse teamDto,
            SeasonResponse seasonDto
    ) {
        return new DriverContractResponse(
                contract.getId(),
                contract.getDriverRole(),
                contract.getContractStart(),
                contract.getContractEnd(),
                contract.getSalary(),
                contract.getStatus(),
                driverDto,
                teamDto,
                seasonDto
        );
    }

    private static DriverContractResponse mapContractToDto(DriverContract contract) {
        Driver driver = contract.getDriver();
        Team team = contract.getTeam();
        Season season = contract.getSeason();

        return mapContractToDto(
                contract,
                new DriverResponse(
                        driver.getId(),
                        driver.getFirstName(),
                        driver.getLastName(),
                        driver.getNationality(),
                        driver.getDateOfBirth(),
                        driver.getNumber(),
                        driver.getStatus()
                ),
                new TeamResponse(
                        team.getId(),
                        team.getName(),
                        team.getShortName(),
                        team.getCountry(),
                        team.getFoundedYear(),
                        team.getTeamPrincipal()
                ),
                new SeasonResponse(
                        season.getId(),
                        season.getName(),
                        season.getYear(),
                        season.getStatus()
                )
        );
    }
}

package me.backend.features.team.service;

import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.team.Team;
import me.backend.features.team.dto.CreateTeamRequest;
import me.backend.features.team.dto.TeamResponse;
import me.backend.features.team.dto.UpdateTeamRequest;
import me.backend.features.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamService::mapTeamToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamResponse getTeamById(long id) {
        return teamRepository.findById(id)
                .map(TeamService::mapTeamToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));
    }

    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request) {
        if (teamRepository.existsByName(request.name())) {
            throw new AlreadyExistsException("Team with name '" + request.name() + "' already exists");
        }
        if (teamRepository.existsByShortName(request.shortName())) {
            throw new AlreadyExistsException("Team with short name '" + request.shortName() + "' already exists");
        }

        var newTeam = new Team(
                request.name(),
                request.shortName(),
                request.country(),
                request.foundedYear(),
                request.teamPrincipal(),
                OffsetDateTime.now()
        );

        teamRepository.save(newTeam);

        return mapTeamToDto(newTeam);
    }

    @Transactional
    public void deleteTeamById(long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team with id " + id + " not found");
        }

        teamRepository.deleteById(id);
    }

    @Transactional
    public TeamResponse updateTeam(long id, UpdateTeamRequest request) {
        Team databaseTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + id + " not found"));

        if (teamRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new AlreadyExistsException("Team with name '" + request.name() + "' already exists");
        }

        databaseTeam.setName(request.name());
        databaseTeam.setCountry(request.country());
        databaseTeam.setFoundedYear(request.foundedYear());
        databaseTeam.setTeamPrincipal(request.teamPrincipal());

        teamRepository.save(databaseTeam);

        return mapTeamToDto(databaseTeam);
    }

    private static TeamResponse mapTeamToDto(Team t) {
        return new TeamResponse(
                t.getId(),
                t.getName(),
                t.getShortName(),
                t.getCountry(),
                t.getFoundedYear(),
                t.getTeamPrincipal()
        );
    }
}

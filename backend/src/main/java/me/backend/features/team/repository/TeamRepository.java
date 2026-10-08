package me.backend.features.team.repository;

import me.backend.features.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByShortName(String shortName);

    Optional<Team> findByName(String name);

    boolean existsByShortName(String shortName);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}

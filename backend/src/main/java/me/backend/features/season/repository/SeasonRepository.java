package me.backend.features.season.repository;

import me.backend.features.season.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByYear(Integer year);

    boolean existsByYearAndIdNot(Integer year, Long id);
}

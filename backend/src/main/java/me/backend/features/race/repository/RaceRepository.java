package me.backend.features.race.repository;

import me.backend.features.race.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {
    boolean existsBySeasonIdAndRoundNumber(Long seasonId, Integer roundNumber);

    boolean existsBySeasonIdAndRoundNumberAndIdNot(Long seasonId, Integer roundNumber, Long id);

    @Query("SELECT r FROM Race r JOIN FETCH r.circuit JOIN FETCH r.season")
    List<Race> findAllWithDetails();

    @Query("SELECT r FROM Race r JOIN FETCH r.circuit JOIN FETCH r.season WHERE r.id = :id")
    Optional<Race> findByIdWithDetails(@Param("id") Long id);
}

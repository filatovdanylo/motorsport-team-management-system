package me.backend.features.race.repository;

import me.backend.features.race.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
    @Query("SELECT rr FROM RaceResult rr JOIN FETCH rr.driver JOIN FETCH rr.race JOIN FETCH rr.race.circuit JOIN FETCH rr.race.season")
    List<RaceResult> getAllWithDetails();

    @Query("SELECT rr FROM RaceResult rr JOIN FETCH rr.driver JOIN FETCH rr.race JOIN FETCH rr.race.circuit JOIN FETCH rr.race.season WHERE rr.id = :id")
    Optional<RaceResult> findByIdWithDetails(@Param("id") Long id);

    boolean existsByDriverIdAndRaceId(Long driverId, Long raceId);

    boolean existsByDriverIdAndRaceIdAndIdNot(Long driverId, Long raceId, Long id);
}

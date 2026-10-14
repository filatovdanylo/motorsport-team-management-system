package me.backend.features.contract.repository;

import me.backend.features.contract.DriverContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverContractRepository extends JpaRepository<DriverContract, Long> {
    @Query("""
            SELECT dc FROM DriverContract dc
            JOIN FETCH dc.driver
            JOIN FETCH dc.team
            JOIN FETCH dc.season
            """)
    List<DriverContract> findAllWithDetails();

    @Query("""
            SELECT dc FROM DriverContract dc
            JOIN FETCH dc.driver d
            JOIN FETCH dc.team
            JOIN FETCH dc.season
            WHERE d.id = :driverId
            """)
    List<DriverContract> findAllByDriverIdWithDetails(@Param("driverId") Long driverId);

    @Query("""
            SELECT dc FROM DriverContract dc
            JOIN FETCH dc.driver
            JOIN FETCH dc.team
            JOIN FETCH dc.season
            WHERE dc.id = :id
            """)
    Optional<DriverContract> findByIdWithDetails(@Param("id") Long id);

    boolean existsByDriverIdAndTeamIdAndSeasonId(Long driverId, Long teamId, Long seasonId);

    boolean existsByDriverIdAndTeamIdAndSeasonIdAndIdNot(Long driverId, Long teamId, Long seasonId, Long id);
}

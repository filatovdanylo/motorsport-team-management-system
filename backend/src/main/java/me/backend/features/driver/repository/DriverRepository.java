package me.backend.features.driver.repository;

import me.backend.features.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByNumber(Integer number);

    boolean existsByNumber(Integer number);

    boolean existsByNumberAndIdNot(Integer number, Long id);
}

package me.backend.features.contract.dto;

import me.backend.features.contract.DriverContract;
import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.team.dto.TeamResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DriverContractResponse(
        Long id,
        DriverContract.DriverRole driverRole,
        LocalDate contractStart,
        LocalDate contractEnd,
        BigDecimal salary,
        DriverContract.Status status,
        DriverResponse driver,
        TeamResponse team,
        SeasonResponse season
) {
}

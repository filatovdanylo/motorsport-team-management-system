package me.backend.features.contract.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import me.backend.features.contract.DriverContract;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateDriverContractRequest(
        @NotNull
        @Positive
        Long driverId,

        @NotNull
        @Positive
        Long teamId,

        @NotNull
        @Positive
        Long seasonId,

        @NotNull
        DriverContract.DriverRole driverRole,

        @NotNull
        LocalDate contractStart,

        @NotNull
        LocalDate contractEnd,

        @NotNull
        @PositiveOrZero
        @Digits(integer = 13, fraction = 2)
        BigDecimal salary,

        @NotNull
        DriverContract.Status status
) {
    @AssertTrue(message = "contractEnd must not be before contractStart")
    public boolean isContractPeriodValid() {
        if (contractStart == null || contractEnd == null) {
            return true;
        }

        return contractEnd.isAfter(contractStart) || contractEnd.isEqual(contractStart);
    }
}

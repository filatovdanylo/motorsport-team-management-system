package me.backend.features.contract;

import jakarta.persistence.*;
import me.backend.features.driver.Driver;
import me.backend.features.season.Season;
import me.backend.features.team.Team;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "driver_contract")
public class DriverContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @Enumerated(EnumType.STRING)
    private DriverRole driverRole;

    private LocalDate contractStart;
    private LocalDate contractEnd;
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum DriverRole {
        PRIMARY_DRIVER,
        SECOND_DRIVER,
        RESERVE_DRIVER
    }

    public enum Status {
        PLANNED,
        ACTIVE,
        COMPLETED,
        TERMINATED
    }

    public DriverContract(Driver driver, Team team, Season season, DriverRole driverRole,
                          LocalDate contractStart, LocalDate contractEnd, BigDecimal salary, Status status) {
        this.driver = driver;
        this.team = team;
        this.season = season;
        this.driverRole = driverRole;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.salary = salary;
        this.status = status;
    }

    public DriverContract() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public DriverRole getDriverRole() {
        return driverRole;
    }

    public void setDriverRole(DriverRole driverRole) {
        this.driverRole = driverRole;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

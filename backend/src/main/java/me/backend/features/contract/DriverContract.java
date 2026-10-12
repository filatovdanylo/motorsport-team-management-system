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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DriverRole driverRole;

    @Column(nullable = false)
    private LocalDate contractStart;

    @Column(nullable = false)
    private LocalDate contractEnd;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
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

    public DriverContract(DriverRole driverRole, LocalDate contractStart, LocalDate contractEnd,
                          BigDecimal salary, Status status) {
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

package me.backend.features.race;

import jakarta.persistence.*;
import me.backend.features.driver.Driver;

import java.math.BigDecimal;

@Entity
@Table(name = "race_result")
public class RaceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private Integer position;
    private BigDecimal points;
    private boolean fastestLap;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long totalRaceTimeMs;

    public enum Status {
        FINISHED,
        DNF,
        DNS,
        DSQ,
        DNQ
    }

    public RaceResult(Race race, Driver driver, Integer position, BigDecimal points,
                      boolean fastestLap, Status status, Long totalRaceTimeMs) {
        this.race = race;
        this.driver = driver;
        this.position = position;
        this.points = points;
        this.fastestLap = fastestLap;
        this.status = status;
        this.totalRaceTimeMs = totalRaceTimeMs;
    }

    public RaceResult() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public boolean isFastestLap() {
        return fastestLap;
    }

    public void setFastestLap(boolean fastestLap) {
        this.fastestLap = fastestLap;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTotalRaceTimeMs() {
        return totalRaceTimeMs;
    }

    public void setTotalRaceTimeMs(Long totalRaceTimeMs) {
        this.totalRaceTimeMs = totalRaceTimeMs;
    }
}

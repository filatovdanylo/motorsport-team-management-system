package me.backend.features.race;

import jakarta.persistence.*;
import me.backend.features.circuit.Circuit;
import me.backend.features.season.Season;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int roundNumber;
    private LocalDate raceDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaceResult> results = new ArrayList<>();

    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }

    public Race(String name, int roundNumber, LocalDate raceDate, Status status, Season season, Circuit circuit) {
        this.name = name;
        this.roundNumber = roundNumber;
        this.raceDate = raceDate;
        this.status = status;
        this.season = season;
        this.circuit = circuit;
    }

    public Race() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(LocalDate raceDate) {
        this.raceDate = raceDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public List<RaceResult> getResults() {
        return results;
    }

    public void setResults(List<RaceResult> results) {
        this.results = results;
    }

    public void addResult(RaceResult result) {
        results.add(result);
        result.setRace(this);
    }

    public void removeResult(RaceResult result) {
        results.remove(result);
        result.setRace(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

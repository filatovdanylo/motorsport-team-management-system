package me.backend.features.season;

import jakarta.persistence.*;
import me.backend.features.race.Race;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int year;

    @Enumerated(EnumType.STRING)
    private Status status;

    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> races = new ArrayList<>();

    public enum Status {
        UPCOMING,
        ACTIVE,
        COMPLETED
    }

    public Season(String name, int year, Status status, OffsetDateTime createdAt) {
        this.name = name;
        this.year = year;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Season() {}

    public void addRace(Race race) {
        races.add(race);
        race.setSeason(this);
    }

    public void removeRace(Race race) {
        races.remove(race);
        race.setSeason(null);
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

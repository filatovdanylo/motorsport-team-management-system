package me.backend.features.race;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Circuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "circuit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> races = new ArrayList<>();

    private String name;
    private String country;
    private String city;
    private BigDecimal lengthKm;
    private int numberOfLaps;
    private OffsetDateTime createdAt;

    public Circuit(List<Race> races, String name, String country, String city, BigDecimal lengthKm, int numberOfLaps, OffsetDateTime createdAt) {
        this.races = races;
        this.name = name;
        this.country = country;
        this.city = city;
        this.lengthKm = lengthKm;
        this.numberOfLaps = numberOfLaps;
        this.createdAt = createdAt;
    }

    public Circuit() {}

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(BigDecimal lengthKm) {
        this.lengthKm = lengthKm;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addRace(Race race) {
        races.add(race);
        race.setCircuit(this);
    }

    public void removeRace(Race race) {
        races.remove(race);
        race.setCircuit(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

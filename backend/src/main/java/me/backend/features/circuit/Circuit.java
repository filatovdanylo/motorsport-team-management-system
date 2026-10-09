package me.backend.features.circuit;

import jakarta.persistence.*;
import me.backend.features.race.Race;

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

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, precision = 7, scale = 3)
    private BigDecimal lengthKm;

    @Column(nullable = false)
    private Integer numberOfLaps;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public Circuit(String name, String country, String city, BigDecimal lengthKm, Integer numberOfLaps, OffsetDateTime createdAt) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.lengthKm = lengthKm;
        this.numberOfLaps = numberOfLaps;
        this.createdAt = createdAt;
    }

    public Circuit() {}

    public void addRace(Race race) {
        races.add(race);
        race.setCircuit(this);
    }

    public void removeRace(Race race) {
        races.remove(race);
        race.setCircuit(null);
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

    public Integer getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(Integer numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
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

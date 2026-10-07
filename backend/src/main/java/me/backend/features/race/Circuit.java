package me.backend.features.race;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "circuits")
public class Circuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "circuit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> races = new ArrayList<>();

    private String name;
    private String country;
    private String city;
    private Double lengthKm;
    private Double numberOfTurnsPerLap;
    private int numberOfLaps;

    public Circuit(List<Race> races, String name, String country, String city, Double lengthKm, Double numberOfTurnsPerLap, int numberOfLaps) {
        this.races = races;
        this.name = name;
        this.country = country;
        this.city = city;
        this.lengthKm = lengthKm;
        this.numberOfTurnsPerLap = numberOfTurnsPerLap;
        this.numberOfLaps = numberOfLaps;
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

    public Double getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(Double lengthKm) {
        this.lengthKm = lengthKm;
    }

    public Double getNumberOfTurnsPerLap() {
        return numberOfTurnsPerLap;
    }

    public void setNumberOfTurnsPerLap(Double numberOfTurnsPerLap) {
        this.numberOfTurnsPerLap = numberOfTurnsPerLap;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
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

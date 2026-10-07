package me.backend.features.season;

import jakarta.persistence.*;
import me.backend.features.race.Race;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seasons")
public class Season {
    @Id
    private Long id;
    private String title;
    private int year;
    private Status status;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> races = new ArrayList<>();

    public enum Status {
        UPCOMING,
        ACTIVE,
        COMPLETED
    }

    public Season(String title, int year, Status status) {
        this.title = title;
        this.year = year;
        this.status = status;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

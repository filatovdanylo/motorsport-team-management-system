package me.backend.features.team;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 10)
    private String shortName;

    @Column(nullable = false, length = 100)
    private String country;

    private Integer foundedYear;

    @Column(length = 150)
    private String teamPrincipal;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public Team(String name, String shortName, String country, Integer foundedYear, String teamPrincipal, OffsetDateTime createdAt) {
        this.name = name;
        this.shortName = shortName;
        this.country = country;
        this.foundedYear = foundedYear;
        this.teamPrincipal = teamPrincipal;
        this.createdAt = createdAt;
    }

    public Team() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getTeamPrincipal() {
        return teamPrincipal;
    }

    public void setTeamPrincipal(String teamPrincipal) {
        this.teamPrincipal = teamPrincipal;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

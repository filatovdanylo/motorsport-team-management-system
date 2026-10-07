package me.backend.features.team;

import jakarta.persistence.*;
import me.backend.features.driver.Driver;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String shortName;
    private String country;
    private int foundedYear;
    private String teamPrinciple;
    private BigDecimal budget;
}

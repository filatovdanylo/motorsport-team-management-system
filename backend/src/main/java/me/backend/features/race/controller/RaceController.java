package me.backend.features.race.controller;

import jakarta.validation.Valid;
import me.backend.features.race.dto.CreateRaceRequest;
import me.backend.features.race.dto.RaceResponse;
import me.backend.features.race.dto.UpdateRaceRequest;
import me.backend.features.race.service.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/races")
public class RaceController {
    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public ResponseEntity<List<RaceResponse>> getAll() {
        return ResponseEntity.ok(raceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceResponse> getRaceById(@PathVariable long id) {
        return ResponseEntity.ok(raceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RaceResponse> createRace(@Valid @RequestBody CreateRaceRequest request) {
        RaceResponse newRace = raceService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRace.id())
                .toUri();

        return ResponseEntity.created(location).body(newRace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable long id) {
        raceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceResponse> updateRace(
            @PathVariable long id,
            @Valid @RequestBody UpdateRaceRequest request
    ) {
        return ResponseEntity.ok(raceService.update(id, request));
    }
}

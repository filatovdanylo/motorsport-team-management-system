package me.backend.features.race.controller;

import me.backend.features.race.dto.CreateRaceResultRequest;
import me.backend.features.race.dto.RaceResultResponse;
import me.backend.features.race.dto.UpdateRaceResultRequest;
import me.backend.features.race.service.RaceResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/race-results")
public class RaceResultController {
    private final RaceResultService raceResultService;
    public RaceResultController(RaceResultService raceResultService) {
        this.raceResultService = raceResultService;
    }

    @GetMapping
    public ResponseEntity<List<RaceResultResponse>> getAll() {
        return ResponseEntity.ok(raceResultService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceResultResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(raceResultService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RaceResultResponse> create(@RequestBody CreateRaceResultRequest request) {
        var newRaceResult = raceResultService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRaceResult.id())
                .toUri();

        return ResponseEntity.created(location).body(newRaceResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        raceResultService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaceResultResponse> update(@PathVariable long id, @RequestBody UpdateRaceResultRequest request) {
        var updatedRaceResult = raceResultService.update(id, request);

        return ResponseEntity.ok(updatedRaceResult);
    }
}

package me.backend.features.season.controller;

import jakarta.validation.Valid;
import me.backend.features.season.dto.CreateSeasonRequest;
import me.backend.features.season.dto.SeasonResponse;
import me.backend.features.season.dto.UpdateSeasonRequest;
import me.backend.features.season.service.SeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {
    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public ResponseEntity<List<SeasonResponse>> getAllSeasons() {
        return ResponseEntity.ok(seasonService.getAllSeasons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonResponse> getSeasonById(@PathVariable long id) {
        return ResponseEntity.ok(seasonService.getSeasonById(id));
    }

    @PostMapping
    public ResponseEntity<SeasonResponse> createSeason(@Valid @RequestBody CreateSeasonRequest request) {
        SeasonResponse newSeason = seasonService.createSeason(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSeason.id())
                .toUri();

        return ResponseEntity.created(location).body(newSeason);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeasonResponse> updateSeason(
            @PathVariable long id,
            @Valid @RequestBody UpdateSeasonRequest request
    ) {
        return ResponseEntity.ok(seasonService.updateSeason(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable long id) {
        seasonService.deleteSeasonById(id);
        return ResponseEntity.noContent().build();
    }
}

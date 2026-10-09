package me.backend.features.circuit.controller;

import jakarta.validation.Valid;
import me.backend.features.circuit.dto.CircuitResponse;
import me.backend.features.circuit.dto.CreateCircuitRequest;
import me.backend.features.circuit.dto.UpdateCircuitRequest;
import me.backend.features.circuit.service.CircuitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/circuits")
public class CircuitController {
    private final CircuitService circuitService;

    public CircuitController(CircuitService circuitService) {
        this.circuitService = circuitService;
    }

    @GetMapping
    public ResponseEntity<List<CircuitResponse>> getAllCircuits() {
        return ResponseEntity.ok(circuitService.getAllCircuits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CircuitResponse> getCircuitById(@PathVariable long id) {
        return ResponseEntity.ok(circuitService.getCircuitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CircuitResponse> updateCircuit(@PathVariable long id, @Valid @RequestBody UpdateCircuitRequest request) {
        return ResponseEntity.ok(circuitService.updateCircuit(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCircuit(@PathVariable long id) {
        circuitService.deleteCircuitById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CircuitResponse> createCircuit(@Valid @RequestBody CreateCircuitRequest createCircuitRequest) {
        CircuitResponse newCircuit = circuitService.createCircuit(createCircuitRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCircuit.id())
                .toUri();

        return ResponseEntity.created(location).body(newCircuit);
    }
}

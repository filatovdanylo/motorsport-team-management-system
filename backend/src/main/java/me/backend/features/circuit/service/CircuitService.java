package me.backend.features.circuit.service;

import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.circuit.Circuit;
import me.backend.features.circuit.dto.CircuitResponse;
import me.backend.features.circuit.dto.CreateCircuitRequest;
import me.backend.features.circuit.dto.UpdateCircuitRequest;
import me.backend.features.circuit.repository.CircuitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CircuitService {
    private final CircuitRepository circuitRepository;

    public CircuitService(CircuitRepository circuitRepository) {
        this.circuitRepository = circuitRepository;
    }

    @Transactional(readOnly = true)
    public List<CircuitResponse> getAllCircuits() {
        return circuitRepository.findAll().stream()
                .map(CircuitService::mapCircuitToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CircuitResponse getCircuitById(long id) {
        return circuitRepository.findById(id)
                .map(CircuitService::mapCircuitToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit not found with id " + id));
    }

    @Transactional
    public CircuitResponse createCircuit(CreateCircuitRequest request) {
        if (circuitRepository.existsByName(request.name())) {
            throw new AlreadyExistsException("Circuit with name '" + request.name() + "' already exists");
        }

        var newCircuit = new Circuit(
                request.name(),
                request.country(),
                request.city(),
                request.lengthKm(),
                request.numberOfLaps(),
                OffsetDateTime.now()
        );

        circuitRepository.save(newCircuit);

        return mapCircuitToDto(newCircuit);
    }

    @Transactional
    public void deleteCircuitById(long id) {
        if (!circuitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Circuit with id " + id + " not found");
        }

        circuitRepository.deleteById(id);
    }

    @Transactional
    public CircuitResponse updateCircuit(long id, UpdateCircuitRequest request) {
        Circuit databaseCircuit = circuitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit with id " + id + " not found"));

        if (circuitRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new AlreadyExistsException("Circuit with name '" + request.name() + "' already exists");
        }

        databaseCircuit.setName(request.name());
        databaseCircuit.setCountry(request.country());
        databaseCircuit.setCity(request.city());
        databaseCircuit.setLengthKm(request.lengthKm());
        databaseCircuit.setNumberOfLaps(request.numberOfLaps());

        circuitRepository.save(databaseCircuit);

        return mapCircuitToDto(databaseCircuit);
    }

    private static CircuitResponse mapCircuitToDto(Circuit c) {
        return new CircuitResponse(
                c.getId(),
                c.getName(),
                c.getCountry(),
                c.getCity(),
                c.getLengthKm(),
                c.getNumberOfLaps()
        );
    }
}

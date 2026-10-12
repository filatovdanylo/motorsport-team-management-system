package me.backend.features.contract.controller;

import jakarta.validation.Valid;
import me.backend.features.contract.dto.CreateDriverContractRequest;
import me.backend.features.contract.dto.DriverContractResponse;
import me.backend.features.contract.dto.UpdateDriverContractRequest;
import me.backend.features.contract.service.DriverContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/driver-contracts")
public class DriverContractController {
    private final DriverContractService driverContractService;
    public DriverContractController(DriverContractService driverContractService) {
        this.driverContractService = driverContractService;
    }

    @GetMapping
    public ResponseEntity<List<DriverContractResponse>> getAll() {
        return ResponseEntity.ok(driverContractService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverContractResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(driverContractService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DriverContractResponse> create(@Valid @RequestBody CreateDriverContractRequest request) {
        DriverContractResponse newContract = driverContractService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newContract.id())
                .toUri();

        return ResponseEntity.created(location).body(newContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverContractResponse> update(
            @PathVariable long id,
            @Valid @RequestBody UpdateDriverContractRequest request
    ) {
        return ResponseEntity.ok(driverContractService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        driverContractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

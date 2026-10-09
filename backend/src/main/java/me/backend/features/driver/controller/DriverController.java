package me.backend.features.driver.controller;

import jakarta.validation.Valid;
import me.backend.features.driver.dto.CreateDriverRequest;
import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.driver.dto.UpdateDriverRequest;
import me.backend.features.driver.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@PathVariable long id, @Valid @RequestBody UpdateDriverRequest request) {
        return ResponseEntity.ok(driverService.updateDriver(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable long id) {
        driverService.deleteDriverById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@Valid @RequestBody CreateDriverRequest createDriverRequest) {
        DriverResponse newDriver = driverService.createDriver(createDriverRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDriver.id())
                .toUri();

        return ResponseEntity.created(location).body(newDriver);
    }
}

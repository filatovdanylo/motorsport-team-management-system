package me.backend.features.driver.service;

import me.backend.exception.AlreadyExistsException;
import me.backend.exception.ResourceNotFoundException;
import me.backend.features.driver.Driver;
import me.backend.features.driver.dto.CreateDriverRequest;
import me.backend.features.driver.dto.DriverResponse;
import me.backend.features.driver.dto.UpdateDriverRequest;
import me.backend.features.driver.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional(readOnly = true)
    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(DriverService::mapDriverToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DriverResponse getDriverById(long id) {
        return driverRepository.findById(id)
                .map(DriverService::mapDriverToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + id));
    }

    @Transactional
    public DriverResponse createDriver(CreateDriverRequest request) {
        if (driverRepository.existsByNumber(request.number())) {
            throw new AlreadyExistsException("Driver with number '" + request.number() + "' already exists");
        }

        var newDriver = new Driver(
                request.firstName(),
                request.lastName(),
                request.nationality(),
                request.dateOfBirth(),
                request.number(),
                request.status(),
                OffsetDateTime.now()
        );

        driverRepository.save(newDriver);

        return mapDriverToDto(newDriver);
    }

    @Transactional
    public void deleteDriverById(long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver with id " + id + " not found");
        }

        driverRepository.deleteById(id);
    }

    @Transactional
    public DriverResponse updateDriver(long id, UpdateDriverRequest request) {
        Driver databaseDriver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver with id " + id + " not found"));

        if (driverRepository.existsByNumberAndIdNot(request.number(), id)) {
            throw new AlreadyExistsException("Driver with number '" + request.number() + "' already exists");
        }

        databaseDriver.setFirstName(request.firstName());
        databaseDriver.setLastName(request.lastName());
        databaseDriver.setNationality(request.nationality());
        databaseDriver.setDateOfBirth(request.dateOfBirth());
        databaseDriver.setNumber(request.number());
        databaseDriver.setStatus(request.status());

        driverRepository.save(databaseDriver);

        return mapDriverToDto(databaseDriver);
    }

    private static DriverResponse mapDriverToDto(Driver d) {
        return new DriverResponse(
                d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getNationality(),
                d.getDateOfBirth(),
                d.getNumber(),
                d.getStatus()
        );
    }
}

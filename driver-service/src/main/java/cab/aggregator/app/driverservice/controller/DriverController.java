package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.dto.request.DriverRequest;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Validated
@Tag(name="Driver controller")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Get driver dy Id")
    @GetMapping("/{id}")
    public DriverResponse getDriverById(@PathVariable int id) {
        return driverService.getDriverById(id);
    }

    @Operation(summary = "Get all drivers")
    @GetMapping
    public DriverContainerResponse getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @Operation(summary = "Get all drivers by gender")
    @GetMapping("/driver-by-gender/{gender}")
    public DriverContainerResponse getAllDriversByGender(@Valid @Validated
                      @PathVariable
                      @Pattern(regexp = "^(?i)(male|female)$", message = "{gender.pattern}") String gender) {
        return driverService.getDriversByGender(gender);
    }

    @Operation(summary = "Safe delete driver by Id")
    @DeleteMapping("/safe/{id}")
    public void safeDeleteDriverById(@PathVariable int id) {
        driverService.safeDeleteDriver(id);
    }

    @Operation(summary = "Hard delete driver by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> deleteDriverById(@PathVariable int id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new driver")
    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@Valid @Validated(OnCreate.class)
                                              @RequestBody DriverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(request));
    }

    @Operation(summary = "Update driver by Id")
    @PutMapping("/{id}")
    public DriverResponse updateDriver(@PathVariable int id,
                                       @Valid @Validated(OnUpdate.class) @RequestBody DriverRequest request) {
        return driverService.updateDriver(id, request);
    }
}

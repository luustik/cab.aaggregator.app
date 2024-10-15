package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.dto.request.DriverRequest;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cab.aggregator.app.driverservice.utility.Constants.GENDER_PATTERN;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController implements DriverAPI{

    private final DriverService driverService;

    @GetMapping("/{id}")
    public DriverResponse getDriverById(@PathVariable int id) {
        return driverService.getDriverById(id);
    }

    @GetMapping("/admin")
    public DriverContainerResponse getAllDriversAdmin(@RequestParam(value = "offset", defaultValue = "0") @Min(0) int offset,
                                                      @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) int limit){
        return driverService.getAllDriversAdmin(offset,limit);
    }

    @GetMapping
    public DriverContainerResponse getAllDrivers(@RequestParam(value = "offset", defaultValue = "0") @Min(0) int offset,
                                                 @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) int limit){
        return driverService.getAllDrivers(offset,limit);
    }

    @GetMapping("/driver-by-gender/{gender}")
    public DriverContainerResponse getAllDriversByGender(@Valid @Validated
                      @PathVariable
                      @Pattern(regexp = GENDER_PATTERN, message = "{gender.pattern}") String gender,
                      @RequestParam(value = "offset", defaultValue = "0") @Min(0) int offset,
                      @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) int limit) {
        return driverService.getDriversByGender(gender,offset,limit);
    }

    @DeleteMapping("/safe/{id}")
    public void safeDeleteDriverById(@PathVariable int id) {
        driverService.safeDeleteDriver(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDriverById(@PathVariable int id) {
        driverService.deleteDriver(id);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@Valid @Validated(OnCreate.class)
                                              @RequestBody DriverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(request));
    }

    @PutMapping("/{id}")
    public DriverResponse updateDriver(@PathVariable int id,
                                       @Valid @Validated(OnUpdate.class) @RequestBody DriverRequest request) {
        return driverService.updateDriver(id, request);
    }
}

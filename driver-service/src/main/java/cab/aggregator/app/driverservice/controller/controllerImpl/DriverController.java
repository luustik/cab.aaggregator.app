package cab.aggregator.app.driverservice.controller.controllerImpl;

import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Validated
@Tag(name="Driver controller")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Get driver dy Id")
    @GetMapping("/{id}")
    public DriverResponseDto getDriverById(@PathVariable int id) {
        return driverService.getDriverById(id);
    }

    @Operation(summary = "Get all drivers")
    @GetMapping("/show")
    public List<DriverResponseDto> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @Operation(summary = "Get all drivers by gender")
    @GetMapping("/driver-by-gender/{gender}")
    public List<DriverResponseDto> getAllDriversByGender(@Valid @Validated
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
    public void deleteDriverById(@PathVariable int id) {
        driverService.deleteDriver(id);
    }

    @Operation(summary = "Create new driver")
    @PostMapping("/new-driver")
    public DriverResponseDto createDriver(@Valid @Validated(OnCreate.class)
                                              @RequestBody DriverRequestDto requestDto) {
        return driverService.createDriver(requestDto);
    }

    @Operation(summary = "Update driver by Id")
    @PutMapping("/update-driver/{id}")
    public DriverResponseDto updateDriver(@PathVariable int id,
                                          @Valid @Validated(OnUpdate.class) @RequestBody DriverRequestDto requestDto) {
        return driverService.updateDriver(id, requestDto);
    }
}

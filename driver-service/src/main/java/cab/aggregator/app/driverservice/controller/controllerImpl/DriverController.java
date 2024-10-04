package cab.aggregator.app.driverservice.controller.controllerImpl;

import cab.aggregator.app.driverservice.controller.DriverApi;
import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController implements DriverApi {

    private final DriverService driverService;

    @GetMapping("/{id}")
    public DriverResponseDto getDriverById(@PathVariable int id) {
        return driverService.getDriverById(id);
    }

    @GetMapping("/show")
    public List<DriverResponseDto> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @GetMapping("/driver-by-gender/{gender}")
    public List<DriverResponseDto> getAllDriversByGender(@PathVariable String gender) {
        return driverService.getDriversByGender(gender);
    }

    @DeleteMapping("/{id}")
    public void deleteDriverById(@PathVariable int id) {
        driverService.deleteDriver(id);
    }

    @PostMapping("/new-driver")
    public DriverResponseDto createDriver(@Valid @Validated(OnCreate.class)
                                              @RequestBody DriverRequestDto requestDto) {
        return driverService.createDriver(requestDto);
    }

    @PutMapping("/update-driver/{id}")
    public DriverResponseDto updateDriver(@PathVariable int id,
                                          @Valid @Validated(OnUpdate.class) @RequestBody DriverRequestDto requestDto) {
        return driverService.updateDriver(id, requestDto);
    }
}

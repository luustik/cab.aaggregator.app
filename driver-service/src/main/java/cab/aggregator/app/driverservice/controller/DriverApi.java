package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name="Driver controller")
public interface DriverApi {

    @Operation(summary = "Get driver dy Id")
    DriverResponseDto getDriverById(int id);

    @Operation(summary = "Get all drivers")
    List<DriverResponseDto> getAllDrivers();

    @Operation(summary = "Get all drivers by gender")
    List<DriverResponseDto> getAllDriversByGender(String gender);

    @Operation(summary = "Delete driver by Id")
    void deleteDriverById(int id);

    @Operation(summary = "Create new driver")
    DriverResponseDto createDriver(DriverRequestDto requestDto);

    @Operation(summary = "Update driver by Id")
    DriverResponseDto updateDriver(int id, DriverRequestDto requestDto);
}

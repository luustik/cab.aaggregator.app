package cab.aggregator.app.driverservice.service;

import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;

import java.util.List;

public interface DriverService {

    DriverResponseDto getDriverById(int driverId);

    List<DriverResponseDto> getAllDrivers();

    List<DriverResponseDto> getDriversByGender(String gender);

    void deleteDriver(int driverId);

    DriverResponseDto createDriver(DriverRequestDto driverRequestDto);

    DriverResponseDto updateDriver(int id,DriverRequestDto driverRequestDto);

}

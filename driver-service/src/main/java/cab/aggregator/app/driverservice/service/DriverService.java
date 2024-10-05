package cab.aggregator.app.driverservice.service;

import cab.aggregator.app.driverservice.dto.request.DriverRequest;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;

public interface DriverService {

    DriverResponse getDriverById(int driverId);

    DriverContainerResponse getAllDrivers();

    DriverContainerResponse getDriversByGender(String gender);

    void safeDeleteDriver(int driverId);

    void deleteDriver(int driverId);

    DriverResponse createDriver(DriverRequest driverRequestDto);

    DriverResponse updateDriver(int id, DriverRequest driverRequestDto);

}

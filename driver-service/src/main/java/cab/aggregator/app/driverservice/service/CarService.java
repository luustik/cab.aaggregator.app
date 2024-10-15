package cab.aggregator.app.driverservice.service;

import cab.aggregator.app.driverservice.dto.request.CarRequest;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponse;
import cab.aggregator.app.driverservice.dto.response.CarResponse;

public interface CarService {

    CarResponse getCarById(int carId);

    CarContainerResponse getAllCars(int offset, int limit);

    CarResponse getCarByCarNumber(String carNumber);

    CarContainerResponse getAllCarsByDriverId(int driverId,int offset, int limit);

    CarResponse createCar(CarRequest carRequestDto);

    CarResponse updateCar(int carId, CarRequest carRequestDto);

    void deleteCar(int carId);

}

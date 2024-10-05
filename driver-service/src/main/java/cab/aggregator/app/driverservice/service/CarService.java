package cab.aggregator.app.driverservice.service;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponseDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;

public interface CarService {

    CarResponseDto getCarById(int carId);

    CarContainerResponseDto getAllCars();

    CarResponseDto getCarByCarNumber(String carNumber);

    CarContainerResponseDto getAllCarsByDriverId(int driverId);

    CarResponseDto createCar(CarRequestDto carRequestDto);

    CarResponseDto updateCar(int carId, CarRequestDto carRequestDto);

    void deleteCar(int carId);

}

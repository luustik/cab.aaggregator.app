package cab.aggregator.app.driverservice.service;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto getCarById(int carId);

    List<CarResponseDto> getAllCars();

    CarResponseDto getCarByCarNumber(String carNumber);

    List<CarResponseDto> getAllCarsByDriverId(int driverId);

    CarResponseDto createCar(CarRequestDto carRequestDto);

    CarResponseDto updateCar(int carId, CarRequestDto carRequestDto);

    void deleteCar(int carId);

}

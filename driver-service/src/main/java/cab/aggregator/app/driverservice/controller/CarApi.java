package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name="Car controller")
public interface CarApi {

    @Operation(summary = "Get car dy Id")
    CarResponseDto getCarById( int id );

    @Operation(summary = "Get all cars")
    List<CarResponseDto> getAllCars();

    @Operation(summary = "Get car by car number")
    CarResponseDto getCarByCarNumber(String carNumber);

    @Operation(summary = "Get all cars by driver id")
    List<CarResponseDto> getAllCarsByDriverId(int driverId);

    @Operation(summary = "Create new car")
    CarResponseDto createCar( CarRequestDto car );

    @Operation(summary = "Update car by id")
    CarResponseDto updateCar( int id, CarRequestDto car );

    @Operation(summary = "Delete car by id")
    void deleteCarById( int id );

}

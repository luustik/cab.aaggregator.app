package cab.aggregator.app.driverservice.controller.controllerImpl;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Validated
@Tag(name="Car controller")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get car by Id")
    @GetMapping("/{id}")
    public CarResponseDto getCarById(@PathVariable int id){
        return carService.getCarById(id);
    }

    @Operation(summary = "Get all cars")
    @GetMapping("/show")
    public List<CarResponseDto> getAllCars(){
        return carService.getAllCars();
    }

    @Operation(summary = "Get car by car number")
    @GetMapping("/car-by-number/{carNumber}")
    public CarResponseDto getCarByCarNumber(@PathVariable String carNumber){
        return carService.getCarByCarNumber(carNumber);
    }

    @Operation(summary = "Get all cars by driver id")
    @GetMapping("/cars-driver/{driverId}")
    public List<CarResponseDto> getAllCarsByDriverId(@PathVariable int driverId){
        return carService.getAllCarsByDriverId(driverId);
    }

    @Operation(summary = "Delete car by id")
    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable int id){
        carService.deleteCar(id);
    }

    @Operation(summary = "Create new car")
    @PostMapping("/new-car")
    public CarResponseDto createCar(@Valid @Validated(OnCreate.class) @RequestBody CarRequestDto requestDto) {
        return carService.createCar(requestDto);
    }

    @Operation(summary = "Update car by id")
    @PutMapping("/edit-car/{id}")
    public CarResponseDto updateCar(@PathVariable int id,
                                         @Valid @Validated(OnUpdate.class) @RequestBody CarRequestDto requestDto) {
        return carService.updateCar(id, requestDto);
    }
}

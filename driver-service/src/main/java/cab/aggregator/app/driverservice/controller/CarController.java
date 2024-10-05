package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.dto.request.CarRequest;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponse;
import cab.aggregator.app.driverservice.dto.response.CarResponse;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Validated
@Tag(name="Car controller")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get car by Id")
    @GetMapping("/{id}")
    public CarResponse getCarById(@PathVariable int id){
        return carService.getCarById(id);
    }

    @Operation(summary = "Get all cars")
    @GetMapping
    public CarContainerResponse getAllCars(){
        return carService.getAllCars();
    }

    @Operation(summary = "Get car by car number")
    @GetMapping("/car-by-number/{carNumber}")
    public CarResponse getCarByCarNumber(@PathVariable String carNumber){
        return carService.getCarByCarNumber(carNumber);
    }

    @Operation(summary = "Get all cars by driver id")
    @GetMapping("/cars-driver/{driverId}")
    public CarContainerResponse getAllCarsByDriverId(@PathVariable int driverId){
        return carService.getAllCarsByDriverId(driverId);
    }

    @Operation(summary = "Delete car by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> deleteCarById(@PathVariable int id){
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new car")
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @Validated(OnCreate.class) @RequestBody CarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(carService.createCar(request));
    }

    @Operation(summary = "Update car by id")
    @PutMapping("/{id}")
    public CarResponse updateCar(@PathVariable int id,
                                 @Valid @Validated(OnUpdate.class) @RequestBody CarRequest request) {
        return carService.updateCar(id, request);
    }
}

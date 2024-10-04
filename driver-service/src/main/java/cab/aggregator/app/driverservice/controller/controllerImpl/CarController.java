package cab.aggregator.app.driverservice.controller.controllerImpl;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import cab.aggregator.app.driverservice.dto.validation.OnCreate;
import cab.aggregator.app.driverservice.dto.validation.OnUpdate;
import cab.aggregator.app.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public CarResponseDto getCarById(@PathVariable int id){
        return carService.getCarById(id);
    }

    @GetMapping("/show")
    public List<CarResponseDto> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/car-by-number/{carNumber}")
    public CarResponseDto getCarByCarNumber(@PathVariable String carNumber){
        return carService.getCarByCarNumber(carNumber);
    }

    @GetMapping("/cars-driver/{driverId}")
    public List<CarResponseDto> getAllCarsByDriverId(@PathVariable int driverId){
        return carService.getAllCarsByDriverId(driverId);
    }

    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable int id){
        carService.deleteCar(id);
    }

    @PostMapping("/new-car")
    public CarResponseDto createCar(@Validated(OnCreate.class) @RequestBody CarRequestDto requestDto) {
        return carService.createCar(requestDto);
    }

    @PutMapping("/edit-car/{id}")
    public CarResponseDto updateCar(@PathVariable int id,
                                          @Validated(OnUpdate.class) @RequestBody CarRequestDto requestDto) {
        return carService.updateCar(id, requestDto);
    }
}

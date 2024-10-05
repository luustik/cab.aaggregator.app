package cab.aggregator.app.driverservice.service.impl;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponseDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import cab.aggregator.app.driverservice.entity.Car;
import cab.aggregator.app.driverservice.entity.Driver;
import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.exception.ResourceAlreadyExistsException;
import cab.aggregator.app.driverservice.mapper.CarContainerResponseMapper;
import cab.aggregator.app.driverservice.mapper.CarMapper;
import cab.aggregator.app.driverservice.repository.CarRepository;
import cab.aggregator.app.driverservice.repository.DriverRepository;
import cab.aggregator.app.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cab.aggregator.app.driverservice.utility.ResourceName.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarContainerResponseMapper carContainerResponseMapper;
    private final DriverRepository driverRepository;

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto getCarById(int carId) {
        return carMapper.toDto(findCarById(carId));
    }


    @Transactional(readOnly = true)
    @Override
    public CarContainerResponseDto getAllCars() {
        return carContainerResponseMapper.toDto(carMapper.toDtoList(carRepository.findAll()));
    }

    @Transactional(readOnly = true)
    @Override
    public CarResponseDto getCarByCarNumber(String carNumber) {
        return carMapper.toDto(findCarByCarNumber(carNumber));
    }

    @Transactional(readOnly = true)
    @Override
    public CarContainerResponseDto getAllCarsByDriverId(int driverId) {
        return carContainerResponseMapper.toDto(carMapper.toDtoList(carRepository.findAllByDriverId(driverId)));
    }

    @Override
    @Transactional
    public CarResponseDto createCar(CarRequestDto carRequestDto) {
        checkIfCarUnique(carRequestDto);
        Car car = carMapper.toEntity(carRequestDto);
        car.setDriver(findDriverById(carRequestDto));
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarResponseDto updateCar(int carId, CarRequestDto carRequestDto) {
        Car car = findCarById(carId);
        if(!car.getCarNumber().equals(carRequestDto.carNumber())){
            checkIfCarUnique(carRequestDto);
        }
        carMapper.updateCarFromDto(carRequestDto, car);
        car.setDriver(findDriverById(carRequestDto));
        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public void deleteCar(int carId) {
        Car car = findCarById(carId);
        carRepository.delete(car);
    }

    private void checkIfCarUnique(CarRequestDto carRequestDto) {
        if(carRepository.existsByCarNumber(carRequestDto.carNumber())) {
            throw new ResourceAlreadyExistsException(CAR, carRequestDto.carNumber());
        }
    }

    private Driver findDriverById(CarRequestDto carRequestDto) {
        return driverRepository.findById(carRequestDto.driverId())
                .filter(driver -> !driver.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException(DRIVER, carRequestDto.driverId()));
    }

    private Car findCarByCarNumber(String carNumber) {
        return carRepository.findByCarNumber(carNumber).orElseThrow(()->{
            return new EntityNotFoundException(CAR,carNumber);
        });
    }

    private Car findCarById(int carId) {
        return carRepository.findById(carId).orElseThrow(()->{
            return new EntityNotFoundException(CAR, carId);
        });
    }
}

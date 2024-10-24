package cab.aggregator.app.driverservice.service.impl;

import cab.aggregator.app.driverservice.dto.request.CarRequest;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponse;
import cab.aggregator.app.driverservice.dto.response.CarResponse;
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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static cab.aggregator.app.driverservice.utility.Constants.CAR;
import static cab.aggregator.app.driverservice.utility.Constants.DRIVER;
import static cab.aggregator.app.driverservice.utility.Constants.ENTITY_NOT_FOUND_MESSAGE;
import static cab.aggregator.app.driverservice.utility.Constants.RESOURCE_ALREADY_EXIST_MESSAGE;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarContainerResponseMapper carContainerResponseMapper;
    private final DriverRepository driverRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCarById(int carId) {
        return carMapper.toDto(findCarById(carId));
    }

    @Transactional(readOnly = true)
    @Override
    public CarContainerResponse getAllCars(int offset, int limit) {
        return carContainerResponseMapper.toContainer(carRepository
                .findAll(PageRequest.of(offset, limit))
                .map(carMapper::toDto));
    }

    @Transactional(readOnly = true)
    @Override
    public CarResponse getCarByCarNumber(String carNumber) {
        return carMapper.toDto(findCarByCarNumber(carNumber));
    }

    @Transactional(readOnly = true)
    @Override
    public CarContainerResponse getAllCarsByDriverId(int driverId, int offset, int limit) {
        return carContainerResponseMapper.toContainer(carRepository
                .findAllByDriverId(driverId, PageRequest.of(offset, limit))
                .map(carMapper::toDto));
    }

    @Override
    @Transactional
    public CarResponse createCar(CarRequest carRequestDto) {
        checkIfCarUnique(carRequestDto);
        Car car = carMapper.toEntity(carRequestDto);
        car.setDriver(findDriverById(carRequestDto));
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarResponse updateCar(int carId, CarRequest carRequestDto) {
        Car car = findCarById(carId);
        if (!car.getCarNumber().equals(carRequestDto.carNumber())) {
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

    private void checkIfCarUnique(CarRequest carRequestDto) {
        if (carRepository.existsByCarNumber(carRequestDto.carNumber())) {
            throw new ResourceAlreadyExistsException(messageSource.getMessage(RESOURCE_ALREADY_EXIST_MESSAGE,
                    new Object[]{CAR, carRequestDto.carNumber()}, Locale.getDefault()));
        }
    }

    private Driver findDriverById(CarRequest carRequestDto) {
        return driverRepository.findById(carRequestDto.driverId())
                .filter(driver -> !driver.isDeleted())
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE,
                                new Object[]{DRIVER, carRequestDto.driverId()}, Locale.getDefault())));
    }

    private Car findCarByCarNumber(String carNumber) {
        return carRepository.findByCarNumber(carNumber)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE,
                                new Object[]{CAR, carNumber}, Locale.getDefault())));
    }

    private Car findCarById(int carId) {
        return carRepository.findById(carId)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE,
                                new Object[]{CAR, carId}, Locale.getDefault())));
    }
}

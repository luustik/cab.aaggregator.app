package cab.aggregator.app.driverservice.service.impl;

import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import cab.aggregator.app.driverservice.entity.Driver;
import cab.aggregator.app.driverservice.entity.enums.Gender;
import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.exception.ResourceAlreadyExistsException;
import cab.aggregator.app.driverservice.mapper.DriverMapper;
import cab.aggregator.app.driverservice.repository.DriverRepository;
import cab.aggregator.app.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static cab.aggregator.app.driverservice.utility.ResourceName.*;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDto getDriverById(int driverId) {
        return driverMapper.toDto(findDriverById(driverId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDto> getAllDrivers() {
        return driverMapper.toDtoList(driverRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDto> getDriversByGender(String gender) {
        return driverMapper.toDtoList(driverRepository.findAllByGender(Gender.valueOf(gender.toUpperCase())));
    }

    @Override
    @Transactional
    public void safeDeleteDriver(int driverId) {
        Driver driver = findDriverById(driverId);
        driver.setDeleted(true);
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void deleteDriver(int driverId) {
        Driver driver = findDriverById(driverId);
        driverRepository.delete(driver);
    }

    @Override
    @Transactional
    public DriverResponseDto createDriver(DriverRequestDto driverRequestDto) {
        Driver driver = checkIfDriverDelete(driverRequestDto);
        if(driver != null) {
            driver.setDeleted(false);
            driverRepository.save(driver);
            return driverMapper.toDto(driver);
        }
        checkIfDriverUnique(driverRequestDto);
        driver = driverMapper.toEntity(driverRequestDto);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    @Transactional
    public DriverResponseDto updateDriver(int id, DriverRequestDto driverRequestDto) {
        Driver driver = findDriverById(id);
        if (!driverRequestDto.email().equals(driver.getEmail())) {
            checkIfEmailUnique(driverRequestDto);
        }
        if(!driverRequestDto.phoneNumber().equals(driver.getPhoneNumber())){
            checkIfPhoneNumberUnique(driverRequestDto);
        }
        driverMapper.updateDriverFromDto(driverRequestDto, driver);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    private Driver checkIfDriverDelete(DriverRequestDto driverRequestDto) {
        List<Driver> drivers = driverRepository.findByDeletedTrue();
        return drivers.stream()
                .filter(obj -> obj.getName().equals(driverRequestDto.name()))
                .filter(obj -> obj.getEmail().equals(driverRequestDto.email()))
                .filter(obj -> obj.getPhoneNumber().equals(driverRequestDto.phoneNumber()))
                .filter(obj -> obj.getGender().name().equals(driverRequestDto.gender().toUpperCase()))
                .findFirst()
                .orElse(null);
    }


    private void checkIfEmailUnique(DriverRequestDto driverRequestDto) {

        if(driverRepository.existsByEmail(driverRequestDto.email())) {
            throw new ResourceAlreadyExistsException(DRIVER, driverRequestDto.email());
        }

    }

    private void checkIfPhoneNumberUnique(DriverRequestDto driverRequestDto) {
        if(driverRepository.existsByPhoneNumber(driverRequestDto.phoneNumber())) {
            throw new ResourceAlreadyExistsException(DRIVER, driverRequestDto.phoneNumber());
        }
    }

    private void checkIfDriverUnique(DriverRequestDto driverRequestDto) {
        checkIfEmailUnique(driverRequestDto);
        checkIfPhoneNumberUnique(driverRequestDto);
    }

//    private Driver findDriverById(int driverId) {
//        return driverRepository.findById(driverId)
//                .filter(driver -> !driver.isDeleted())
//                .orElseThrow(() -> new EntityNotFoundException(DRIVER, driverId));
//    }

    private Driver findDriverById(int driverId) {
        return driverRepository.findById(driverId).orElseThrow(()->{
            return new EntityNotFoundException(DRIVER,driverId);
        });
    }
}

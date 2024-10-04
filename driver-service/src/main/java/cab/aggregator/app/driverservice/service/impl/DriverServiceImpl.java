package cab.aggregator.app.driverservice.service.impl;

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
    public void deleteDriver(int driverId) {
        Driver driver = findDriverById(driverId);
        driverRepository.delete(driver);
    }

    @Override
    @Transactional
    public DriverResponseDto createDriver(DriverRequestDto driverRequestDto) {
        checkIfDriverUnique(driverRequestDto);
        Driver driver = driverMapper.toEntity(driverRequestDto);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    @Override
    @Transactional
    public DriverResponseDto updateDriver(int id, DriverRequestDto driverRequestDto) {
        Driver driver = findDriverById(id);
        if (!driverRequestDto.email().equals(driver.getEmail())
                || !driverRequestDto.phoneNumber().equals(driver.getPhoneNumber())) {
            checkIfDriverUnique(driverRequestDto);
        }
        driverMapper.updateDriverFromDto(driverRequestDto, driver);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    private void checkIfDriverUnique(DriverRequestDto driverRequestDto) {

        if(driverRepository.existsByEmail(driverRequestDto.email())
                && driverRepository.existsByPhoneNumber(driverRequestDto.phoneNumber())) {
            throw new ResourceAlreadyExistsException(DRIVER, driverRequestDto.phoneNumber(),driverRequestDto.email());
        }
    }

    private Driver findDriverById(int driverId) {
        return driverRepository.findById(driverId).orElseThrow(()->{
            return new EntityNotFoundException(DRIVER,driverId);
        });
    }
}

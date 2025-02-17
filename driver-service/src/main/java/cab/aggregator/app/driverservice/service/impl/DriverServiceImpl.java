package cab.aggregator.app.driverservice.service.impl;

import cab.aggregator.app.driverservice.dto.request.DriverRequest;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;
import cab.aggregator.app.driverservice.entity.Driver;
import cab.aggregator.app.driverservice.entity.enums.Gender;
import cab.aggregator.app.driverservice.exception.AccessDeniedException;
import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.exception.ResourceAlreadyExistsException;
import cab.aggregator.app.driverservice.mapper.DriverContainerResponseMapper;
import cab.aggregator.app.driverservice.mapper.DriverMapper;
import cab.aggregator.app.driverservice.repository.DriverRepository;
import cab.aggregator.app.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static cab.aggregator.app.driverservice.utility.Constants.DRIVER;
import static cab.aggregator.app.driverservice.utility.Constants.EMAIL_CLAIM;
import static cab.aggregator.app.driverservice.utility.Constants.ENTITY_NOT_FOUND_MESSAGE;
import static cab.aggregator.app.driverservice.utility.Constants.RESOURCE_ALREADY_EXIST_MESSAGE;
import static cab.aggregator.app.driverservice.utility.Constants.ROLE_ADMIN;
import static cab.aggregator.app.driverservice.utility.Constants.ACCESS_DENIED_MESSAGE;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverContainerResponseMapper driverContainerResponseMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public DriverResponse getDriverById(int driverId) {
        return driverMapper.toDto(findDriverById(driverId));
    }

    @Override
    @Transactional(readOnly = true)
    public DriverContainerResponse getAllDriversAdmin(int offset, int limit) {
        return driverContainerResponseMapper.toContainer(driverRepository
                .findAll(PageRequest.of(offset, limit))
                .map(driverMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public DriverContainerResponse getAllDrivers(int offset, int limit) {
        return driverContainerResponseMapper.toContainer(driverRepository
                .findByDeletedFalse(PageRequest.of(offset, limit))
                .map(driverMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public DriverContainerResponse getDriversByGender(String gender, int offset, int limit) {
        return driverContainerResponseMapper.toContainer(driverRepository
                .findAllByGenderAndDeletedFalse(Gender.valueOf(gender.toUpperCase()), PageRequest.of(offset, limit))
                .map(driverMapper::toDto));
    }

    @Override
    @Transactional
    public void safeDeleteDriver(int driverId, JwtAuthenticationToken token) {
        Driver driver = findDriverById(driverId);
        validateAccessOrThrow(driver, token);
        driver.setDeleted(true);
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void deleteDriver(int driverId) {
        Driver driver = findDriverByIdForAdmin(driverId);
        driverRepository.delete(driver);
    }

    @Override
    @Transactional
    public DriverResponse createDriver(DriverRequest driverRequestDto) {
        Driver driver = checkIfDriverDelete(driverRequestDto);
        if (driver != null) {
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
    public DriverResponse updateDriver(int id, DriverRequest driverRequestDto, JwtAuthenticationToken token) {
        Driver driver = findDriverById(id);
        if (!driverRequestDto.email().equals(driver.getEmail())) {
            checkIfEmailUnique(driverRequestDto);
        }
        if (!driverRequestDto.phoneNumber().equals(driver.getPhoneNumber())) {
            checkIfPhoneNumberUnique(driverRequestDto);
        }
        driverMapper.updateDriverFromDto(driverRequestDto, driver);
        validateAccessOrThrow(driver, token);
        driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    private void validateAccessOrThrow(Driver driver, JwtAuthenticationToken token) {
        if (token.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_ADMIN))) {
            return;
        }

        String userEmail = token.getToken().getClaims().get(EMAIL_CLAIM).toString();
        if (!driver.getEmail().equals(userEmail)) {
            throw new AccessDeniedException(
                    messageSource.getMessage(ACCESS_DENIED_MESSAGE,
                            new Object[]{}, LocaleContextHolder.getLocale())
            );
        }
    }

    private Driver checkIfDriverDelete(DriverRequest driverRequestDto) {
        List<Driver> drivers = driverRepository.findByDeletedTrue();
        return drivers.stream()
                .filter(obj -> obj.getName().equals(driverRequestDto.name()))
                .filter(obj -> obj.getEmail().equals(driverRequestDto.email()))
                .filter(obj -> obj.getPhoneNumber().equals(driverRequestDto.phoneNumber()))
                .filter(obj -> obj.getGender().name().equals(driverRequestDto.gender().toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    private void checkIfEmailUnique(DriverRequest driverRequestDto) {

        if (driverRepository.existsByEmail(driverRequestDto.email())) {
            throw new ResourceAlreadyExistsException(
                    messageSource.getMessage(RESOURCE_ALREADY_EXIST_MESSAGE,
                            new Object[]{DRIVER, driverRequestDto.email()}, Locale.getDefault())
            );
        }

    }

    private void checkIfPhoneNumberUnique(DriverRequest driverRequestDto) {
        if (driverRepository.existsByPhoneNumber(driverRequestDto.phoneNumber())) {
            throw new ResourceAlreadyExistsException(messageSource.getMessage(RESOURCE_ALREADY_EXIST_MESSAGE,
                    new Object[]{DRIVER, driverRequestDto.phoneNumber()}, Locale.getDefault()));
        }
    }

    private void checkIfDriverUnique(DriverRequest driverRequestDto) {
        checkIfEmailUnique(driverRequestDto);
        checkIfPhoneNumberUnique(driverRequestDto);
    }

    private Driver findDriverById(int driverId) {
        return driverRepository.findByIdAndDeletedFalse(driverId).orElseThrow(() ->
                new EntityNotFoundException(messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE,
                        new Object[]{DRIVER, driverId}, Locale.getDefault()))
        );
    }

    private Driver findDriverByIdForAdmin(int driverId) {
        return driverRepository.findById(driverId).orElseThrow(() ->
                new EntityNotFoundException(messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE,
                        new Object[]{DRIVER, driverId}, Locale.getDefault()))
        );
    }
}

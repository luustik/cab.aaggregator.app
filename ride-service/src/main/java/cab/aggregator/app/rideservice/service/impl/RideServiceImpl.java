package cab.aggregator.app.rideservice.service.impl;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.entity.Ride;
import cab.aggregator.app.rideservice.entity.enums.Status;
import cab.aggregator.app.rideservice.exception.EntityNotFoundException;
import cab.aggregator.app.rideservice.mapper.RideContainerMapper;
import cab.aggregator.app.rideservice.mapper.RideMapper;
import cab.aggregator.app.rideservice.repository.RideRepository;
import cab.aggregator.app.rideservice.service.RideService;
import cab.aggregator.app.rideservice.utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

import static cab.aggregator.app.rideservice.utility.ResourceName.RIDE;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    @Autowired
    private final MessageSource messageSource;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RideContainerMapper rideContainerMapper;
    private final CalculateCost calculateCost;
    private final ValidationStatusService validationStatusService;

    @Override
    @Transactional(readOnly = true)
    public RideResponse getRideById(Long rideId) {
        return rideMapper.toDto(findById(rideId));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRides() {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(rideRepository
                                .findAll()));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByDriverId(Long driverId) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(rideRepository
                                .findAllByDriverId(driverId)));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByStatus(String status) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(rideRepository
                                .findAllByStatus(Status.valueOf(status.toUpperCase()))));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByPassengerId(Long passengerId) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(rideRepository
                                .findAllByPassengerId(passengerId)));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllBetweenOrderDateTime(String start, String end) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(rideRepository
                                .findAllByOrderDateTimeBetween(Utilities.convertStringToLocalDateTime(start),
                                        Utilities.convertStringToLocalDateTime(end))));
    }

    @Override
    @Transactional
    public void deleteRide(Long id) {
        Ride ride = findById(id);
        rideRepository.delete(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRideStatus(Long id, String status) {
        Ride ride = findById(id);
        Status newStatus = validationStatusService.validateStatus(ride.getStatus(),
                Status.valueOf(status.toUpperCase()));
        ride.setStatus(newStatus);
        rideRepository.save(ride);
        return rideMapper.toDto(ride);
    }

    @Override
    @Transactional
    public RideResponse createRide(RideRequest rideRequest) {
        Ride ride = rideMapper.toEntity(rideRequest);
        ride.setOrderDateTime(LocalDateTime.now());
        ride.setCost(calculateCost.generatePrice());
        ride.setStatus(Status.CREATED);
        rideRepository.save(ride);
        return rideMapper.toDto(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRide(Long id, RideRequest rideRequest) {
        Ride ride = findById(id);
        rideMapper.updateRideFromDto(rideRequest, ride);
        rideRepository.save(ride);
        return rideMapper.toDto(ride);
    }

    private Ride findById(Long rideId) {
        return rideRepository
                .findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("ENTITY_WITH_ID_NOT_FOUND_MESSAGE",
                        new Object[]{RIDE,rideId}, Locale.getDefault())));
    }
}

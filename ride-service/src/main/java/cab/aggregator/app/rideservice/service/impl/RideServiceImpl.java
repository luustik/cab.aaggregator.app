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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cab.aggregator.app.rideservice.utility.ResourceName.RIDE;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RideContainerMapper rideContainerMapper;

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
                        .toDtoList(checkIfListEmpty(rideRepository
                                .findAll())));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByDriverId(Long driverId) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(checkIfListEmpty(rideRepository
                                .findAllByDriverId(driverId))));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByStatus(String status) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(checkIfListEmpty(rideRepository
                                .findAllByStatus(Status.valueOf(status.toUpperCase())))));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllRidesByPassengerId(Long passengerId) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(checkIfListEmpty(rideRepository
                                .findAllByPassengerId(passengerId))));
    }

    @Override
    @Transactional(readOnly = true)
    public RideContainerResponse getAllBetweenOrderDateTime(String start, String end) {
        return rideContainerMapper
                .toContainer(rideMapper
                        .toDtoList(checkIfListEmpty(rideRepository
                                .findAllByOrderDateTimeBetween(convertStringToLocalDateTime(start),
                                        convertStringToLocalDateTime(end)))));
    }

    @Override
    @Transactional
    public void deleteRide(Long id) {
        Ride ride = findById(id);
        rideRepository.delete(ride);
    }

    @Override
    @Transactional
    public RideResponse createRide(RideRequest rideRequest) {
        Ride ride = rideMapper.toEntity(rideRequest);
        rideRepository.save(ride);
        return rideMapper.toDto(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRideStatus(Long id, String status) {
        Ride ride = findById(id);
        ride.setStatus(Status.valueOf(status.toUpperCase()));
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

    private List<Ride> checkIfListEmpty(List<Ride> rides) {
        if (rides.isEmpty()) {
            throw new EntityNotFoundException(RIDE);
        }
        return rides;
    }

    private LocalDateTime convertStringToLocalDateTime(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(localDateTime, formatter);
    }

    private Ride findById(Long rideId) {
        return rideRepository.findById(rideId).orElseThrow(()->{
            return new EntityNotFoundException(RIDE,rideId);
        });
    }
}

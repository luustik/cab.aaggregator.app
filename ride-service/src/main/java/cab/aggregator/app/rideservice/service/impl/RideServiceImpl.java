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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static cab.aggregator.app.rideservice.utility.ResourceName.RIDE;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

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
    public RideResponse updateRideStatus(Long id, String status) {
        Ride ride = findById(id);
        ride.setStatus(Status.valueOf(status.toUpperCase()));
        rideRepository.save(ride);
        return rideMapper.toDto(ride);
    }

    @Override
    @Transactional
    public RideResponse createRide(RideRequest rideRequest) {
        Ride ride = rideMapper.toEntity(rideRequest);
        ride.setOrderDateTime(LocalDateTime.now());
        ride.setCost(generateCost());
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

    public BigDecimal generateCost() {
        Random RANDOM = new Random();
        int integerPart = RANDOM.nextInt((int) Math.pow(10, 3));
        int fractionalPart = RANDOM.nextInt((int) Math.pow(10, 2));
        BigDecimal price = new BigDecimal(integerPart + "." + String.format("%0" + 2 + "d", fractionalPart));
        return price.setScale(2, RoundingMode.HALF_UP);
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
        return rideRepository.findById(rideId).orElseThrow(()->
             new EntityNotFoundException(RIDE,rideId)
        );
    }
}

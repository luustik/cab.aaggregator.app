package cab.aggregator.app.rideservice.service;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface RideService {

    RideResponse getRideById(Long rideId);

    RideContainerResponse getAllRides();

    RideContainerResponse getAllRidesByDriverId(Long driverId);

    RideContainerResponse getAllRidesByStatus(String status);

    RideContainerResponse getAllRidesByPassengerId(Long passengerId);

    RideContainerResponse getAllBetweenOrderDateTime(String start, String end);

    void deleteRide(Long id);

    RideResponse createRide(RideRequest rideRequest);

    RideResponse updateRideStatus(Long id, String status);

    RideResponse updateRide(Long id, RideRequest rideRequest);
}

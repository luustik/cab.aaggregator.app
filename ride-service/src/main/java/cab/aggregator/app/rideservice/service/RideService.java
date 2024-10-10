package cab.aggregator.app.rideservice.service;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import org.springframework.transaction.annotation.Transactional;

public interface RideService {

    RideResponse getRideById(Long rideId);

    RideContainerResponse getAllRides(Integer offset, Integer limit);

    RideContainerResponse getAllRidesByDriverId(Long driverId,Integer offset, Integer limit);

    RideContainerResponse getAllRidesByStatus(String status,Integer offset, Integer limit);

    RideContainerResponse getAllRidesByPassengerId(Long passengerId, Integer offset, Integer limit);

    RideContainerResponse getAllBetweenOrderDateTime(String start, String end, Integer offset, Integer limit);

    void deleteRide(Long id);

    RideResponse createRide(RideRequest rideRequest);

    RideResponse updateRideStatus(Long id, String status);

    RideResponse updateRide(Long id, RideRequest rideRequest);
}

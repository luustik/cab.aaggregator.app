package cab.aggregator.app.rideservice.utils;

import cab.aggregator.app.rideservice.dto.client.DriverResponse;
import cab.aggregator.app.rideservice.dto.client.PassengerResponse;
import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.entity.Ride;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cab.aggregator.app.rideservice.entity.enums.Status.CREATED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideConstants {

    public static final int COUNT_CALLS_METHOD = 1;
    public static final int OFFSET = 0;
    public static final int LIMIT = 20;

    public static final Long RIDE_ID = 1L;
    public static final Long DRIVER_ID = 1L;
    public static final Long PASSENGER_ID = 1L;
    public static final String RIDE_STATUS = "CREATED";
    public static final String RIDE_INVALID_STATUS = "INVALID_STATUS";
    public static final BigDecimal RIDE_COST = BigDecimal.valueOf(100);
    public static final LocalDateTime RIDE_TIME = LocalDateTime.of(2024, 10, 15, 19, 0, 0);
    public static final LocalDateTime RIDE_START_RANGE_TIME = LocalDateTime.of(2024, 10, 1, 19, 0);
    public static final String RIDE_START_RANGE_TIME_STR = "2024-10-01 19:00";
    public static final String RIDE_START_RANGE_TIME_INVALID_STR = "qwe123";
    public static final LocalDateTime RIDE_END_RANGE_TIME = LocalDateTime.of(2024, 10, 30, 19, 0);
    public static final String RIDE_END_RANGE_TIME_STR = "2024-10-30 19:00";
    public static final String RIDE_END_RANGE_TIME_INVALID_STR = "qwe123";

    public static final Ride RIDE = createRide();

    public static final RideResponse RIDE_RESPONSE = createRideResponse();
    public static final RideRequest RIDE_REQUEST = createRideRequest();

    public static final List<Ride> RIDE_LIST = List.of(RIDE);
    public static final List<RideResponse> RIDE_RESPONSE_LIST = List.of(RIDE_RESPONSE);
    public static final Page<Ride> RIDE_PAGE = new PageImpl<>(RIDE_LIST, PageRequest.of(OFFSET, LIMIT), RIDE_LIST.size());
    public static final Page<RideResponse> RIDE_RESPONSE_PAGE = new PageImpl<>(RIDE_RESPONSE_LIST, PageRequest.of(OFFSET, LIMIT), RIDE_RESPONSE_LIST.size());
    public static final RideContainerResponse RIDE_CONTAINER_RESPONSE = createRideContainerResponse();

    public static final PassengerResponse PASSENGER_RESPONSE = new PassengerResponse(PASSENGER_ID.intValue(), "Kirill", "123@qwe.123", "+375(29)2587413", false);
    public static final DriverResponse DRIVER_RESPONSE = new DriverResponse(PASSENGER_ID.intValue(), "Kirill", "123@qwe.123", "+375(29)2587413", "MALE", false);

    public static final String IMPOSSIBLE_STATUS = "Impossible status";

    public static final String RIDES_ID_URL = "/api/v1/rides/{id}";
    public static final String RIDES_URL = "/api/v1/rides";
    public static final String RIDES_DRIVER_URL = "/api/v1/rides/driver-id/{driverId}";
    public static final String RIDES_PASSENGER_URL = "/api/v1/rides/passenger-id/{passengerId}";
    public static final String RIDES_STATUS_URL = "/api/v1/rides/status/{status}";
    public static final String RIDES_DATE_TIME_URL = "/api/v1/rides/ride-between-date-time/";

    private static Ride createRide() {
        Ride ride = new Ride();
        ride.setId(RIDE_ID);
        ride.setDriverId(DRIVER_ID);
        ride.setPassengerId(PASSENGER_ID);
        ride.setDepartureAddress("departure address");
        ride.setArrivalAddress("arrival address");
        ride.setStatus(CREATED);
        ride.setOrderDateTime(RIDE_TIME);
        ride.setCost(RIDE_COST);
        return ride;
    }

    private static RideResponse createRideResponse() {
        return new RideResponse(RIDE_ID,
                DRIVER_ID,
                PASSENGER_ID,
                "departure address",
                "arrival address",
                RIDE_STATUS,
                RIDE_TIME,
                RIDE_COST);
    }

    private static RideRequest createRideRequest() {
        return new RideRequest(DRIVER_ID,
                PASSENGER_ID,
                "departure address",
                "arrival address");
    }

    private static RideContainerResponse createRideContainerResponse() {
        return RideContainerResponse.builder()
                .items(RIDE_RESPONSE_PAGE.getContent())
                .currentPage(RIDE_RESPONSE_PAGE.getNumber())
                .sizePage(RIDE_RESPONSE_PAGE.getSize())
                .countPages(RIDE_RESPONSE_PAGE.getTotalPages())
                .totalElements(RIDE_RESPONSE_PAGE.getTotalElements())
                .build();
    }
}

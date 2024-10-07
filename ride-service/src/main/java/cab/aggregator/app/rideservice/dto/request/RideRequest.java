package cab.aggregator.app.rideservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideRequest(

        Long driverId,

        Long passengerId,

        String departureAddress,

        String arrivalAddress,

        String status,

        LocalDateTime orderDateTime,

        BigDecimal cost
) {
}

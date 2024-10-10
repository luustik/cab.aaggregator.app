package cab.aggregator.app.rideservice.controller;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.dto.validation.OnCreate;
import cab.aggregator.app.rideservice.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import static cab.aggregator.app.rideservice.utility.Constants.REGEXP_DATE_TIME;
import static cab.aggregator.app.rideservice.utility.Constants.REGEXP_STATUS;

@Tag(name = "Ride Controller")
public interface RideController {

    @Operation(summary = "Get ride by id")
    RideResponse getRideById(Long id);

    @Operation(summary = "Get all rides")
    RideContainerResponse getAllRides(Integer offset,
                                      Integer limit);

    @Operation(summary = "Get all rides by driver id")
    RideContainerResponse getAllRidesByDriverId(@Valid @Validated Long driverId, Integer offset, Integer limit);

    @Operation(summary = "Get all rides by passenger id")
    RideContainerResponse getAllRidesByPassengerId(@Valid @Validated Long passengerId, Integer offset, Integer limit);

    @Operation(summary = "Get all rides by status")
    RideContainerResponse getAllRidesByStatus(@Valid @Validated
                                              @Pattern(regexp = REGEXP_STATUS, message = "{status.pattern}") String status,
                                              Integer offset,
                                              Integer limit);

    @Operation(summary = "Get all rides for period")
    RideContainerResponse getAllBetweenOrderDateTime(@Pattern(regexp = REGEXP_DATE_TIME, message = "{date.pattern}")
                                                     String start,
                                                     @Pattern(regexp = REGEXP_DATE_TIME, message = "{date.pattern}")
                                                     String end,
                                                     Integer offset,
                                                     Integer limit);

    @Operation(summary = "Delete ride by id")
    void deleteRideById(Long id);

    @Operation(summary = "Create new ride")
    ResponseEntity<RideResponse> createRide(@Valid @Validated(OnCreate.class) RideRequest request);

    @Operation(summary = "Update ride by id")
    RideResponse updateRide(Long id, @Valid @Validated(OnUpdate.class) RideRequest request);

    @Operation(summary = "Update ride status by id")
    RideResponse updateRideStatus(Long id, @Valid @Validated(OnUpdate.class)
                                           @Pattern(regexp = REGEXP_STATUS, message = "{status.pattern}")String status);
}

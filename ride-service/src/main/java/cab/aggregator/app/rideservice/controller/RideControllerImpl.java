package cab.aggregator.app.rideservice.controller;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.dto.validation.OnCreate;
import cab.aggregator.app.rideservice.dto.validation.OnUpdate;
import cab.aggregator.app.rideservice.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
@Validated
@Tag(name="Ride Controller")
public class RideControllerImpl {

    private final RideService rideService;

    @GetMapping("/{id}")
    @Operation(summary = "Get ride by id")
    public RideResponse getRideById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @GetMapping
    @Operation(summary = "Get all rides")
    public RideContainerResponse getAllRides() {
        return rideService.getAllRides();
    }

    @GetMapping("/ride-by-driver-id/{driverId}")
    @Operation(summary = "Get all rides by driver id")
    public RideContainerResponse getAllRidesByDriverId(@Valid @Validated
                                                         @PathVariable
                                                         Long driverId) {
        return rideService.getAllRidesByDriverId(driverId);
    }

    @GetMapping("/ride-by-passenger-id/{passengerId}")
    @Operation(summary = "Get all rides by passenger id")
    public RideContainerResponse getAllRidesByPassengerId(@Valid @Validated
                                                       @PathVariable
                                                       Long passengerId) {
        return rideService.getAllRidesByPassengerId(passengerId);
    }

    @GetMapping("/ride-by-status/{status}")
    @Operation(summary = "Get all rides by status")
    public RideContainerResponse getAllRidesByStatus(@Valid @Validated
                                               @PathVariable
                                               @Pattern(regexp = "^(?i)(CREATED|" +
                                                                 "ACCEPTED|" +
                                                                 "WAY_TO_PASSENGER|" +
                                                                 "WAY_TO_DESTINATION|" +
                                                                 "COMPLETED|" +
                                                                 "CANCELLED)$",
                                                       message = "{status.pattern}") String status) {
        return rideService.getAllRidesByStatus(status);
    }

    @GetMapping(value = "/ride-between-date-time/", params = {"start", "end"})
    @Operation(summary = "Get all rides for period")
    public RideContainerResponse getAllBetweenOrderDateTime(
            @RequestParam
            @Pattern(regexp =
            "^(20[0-9]{2}|21[0-0])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$",
            message = "{date.pattern}")
            String start,
            @RequestParam
            @Pattern(regexp =
            "^(20[0-9]{2}|21[0-0])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$",
            message = "{date.pattern}")
            String end) {
        return rideService.getAllBetweenOrderDateTime(start, end);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ride by id")
    public ResponseEntity<RideResponse> deleteRideById(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create new ride")
    public ResponseEntity<RideResponse> createRide(@Valid @Validated(OnCreate.class)
                                                       @RequestBody RideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rideService.createRide(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update ride status by id")
    public RideResponse updateRideStatus(@PathVariable Long id,
                                     @Valid @Validated(OnUpdate.class) @RequestBody String status) {
        return rideService.updateRideStatus(id, status);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ride by id")
    public RideResponse updateRide(@PathVariable Long id,
                                       @Valid @Validated(OnUpdate.class) @RequestBody RideRequest request) {
        return rideService.updateRide(id, request);
    }
}

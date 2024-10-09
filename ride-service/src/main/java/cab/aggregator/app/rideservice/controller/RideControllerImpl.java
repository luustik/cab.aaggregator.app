package cab.aggregator.app.rideservice.controller;

import cab.aggregator.app.rideservice.dto.request.RideRequest;
import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.dto.validation.OnCreate;
import cab.aggregator.app.rideservice.dto.validation.OnUpdate;
import cab.aggregator.app.rideservice.service.RideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static cab.aggregator.app.rideservice.utility.Constants.REGEXP_DATE_TIME;
import static cab.aggregator.app.rideservice.utility.Constants.REGEXP_STATUS;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
@Validated
public class RideControllerImpl implements RideController {

    private final RideService rideService;

    @Override
    @GetMapping("/{id}")
    public RideResponse getRideById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @Override
    @GetMapping
    public RideContainerResponse getAllRides() {
        return rideService.getAllRides();
    }

    @Override
    @GetMapping("/driver-id/{driverId}")
    public RideContainerResponse getAllRidesByDriverId(@Valid @Validated
                                                         @PathVariable Long driverId) {
        return rideService.getAllRidesByDriverId(driverId);
    }

    @Override
    @GetMapping("/passenger-id/{passengerId}")
    public RideContainerResponse getAllRidesByPassengerId(@Valid @Validated
                                                       @PathVariable Long passengerId) {
        return rideService.getAllRidesByPassengerId(passengerId);
    }

    @Override
    @GetMapping("/status/{status}")
    public RideContainerResponse getAllRidesByStatus(@Valid @Validated
                                               @PathVariable
                                               @Pattern(regexp = REGEXP_STATUS, message = "{status.pattern}") String status) {
        return rideService.getAllRidesByStatus(status);
    }


    @Override
    @GetMapping(value = "/ride-between-date-time/", params = {"start", "end"})
    public RideContainerResponse getAllBetweenOrderDateTime(
            @RequestParam
            @Pattern(regexp = REGEXP_DATE_TIME, message = "{date.pattern}")
            String start,
            @RequestParam
            @Pattern(regexp = REGEXP_DATE_TIME, message = "{date.pattern}")
            String end) {
        return rideService.getAllBetweenOrderDateTime(start, end);
    }


    @Override
    @DeleteMapping("/{id}")
    public void deleteRideById(@PathVariable Long id) {
        rideService.deleteRide(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<RideResponse> createRide(@Valid @Validated(OnCreate.class)
                                                       @RequestBody RideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rideService
                        .createRide(request));
    }

    @Override
    @PutMapping("/{id}")
    public RideResponse updateRide(@PathVariable Long id,
                                   @Valid @Validated(OnUpdate.class) @RequestBody RideRequest request) {
        return rideService.updateRide(id, request);
    }


    @PatchMapping("/{id}")
    public RideResponse updateRideStatus(@PathVariable Long id,
                         @Valid @Validated(OnUpdate.class)
                         @Pattern(regexp = REGEXP_STATUS, message = "{status.pattern}")
                         @RequestBody
                         String status) {
        return rideService.updateRideStatus(id, status);
    }
}

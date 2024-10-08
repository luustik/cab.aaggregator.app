package cab.aggregator.app.passengerservice.controller;

import cab.aggregator.app.passengerservice.dto.request.PassengerRequest;
import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;
import cab.aggregator.app.passengerservice.dto.validation.OnCreate;
import cab.aggregator.app.passengerservice.dto.validation.OnUpdate;
import cab.aggregator.app.passengerservice.service.PassengerService;
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
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Validated
@Tag(name="Passenger controller")
public class PassengerControllerImpl {

    private final PassengerService passengerService;

    @Operation(summary = "Get passenger by Id")
    @GetMapping("/{id}")
    public PassengerResponse getPassengerById(@PathVariable int id) {
        return passengerService.getPassengerById(id);
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public PassengerContainerResponse getAllPassengers(){
        return passengerService.getAllPassengers();
    }

    @Operation(summary = "Get passenger by phone")
    @GetMapping("/phone/{phone}")
    public PassengerResponse getPassengerByPhone(@Valid
                                                 @Validated
                                                 @PathVariable
                                                 @Pattern(regexp = "\\+375\\((29|44|33|25)\\)\\d{7}$",
                                                          message = "{passengerPhone.pattern}") String phone) {
        return passengerService.getPassengerByPhone(phone);
    }

    @Operation(summary = "Get passenger by email")
    @GetMapping("/email/{email}")
    public PassengerResponse getPassengerByEmail(@Valid
                                                 @Validated
                                                 @PathVariable
                                                 @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[\\w.-]+$",
                                                          message = "{passengerEmail.pattern}") String email) {
        return passengerService.getPassengerByEmail(email);
    }

    @Operation(summary = "Soft delete passenger")
    @DeleteMapping("/soft/{id}")
    public ResponseEntity<PassengerResponse> softDeleteDriverById(@PathVariable int id) {
        passengerService.softDeletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete passenger")
    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> hardDeleteDriverById(@PathVariable int id) {
        passengerService.hardDeletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new passenger")
    @PostMapping
    public ResponseEntity<PassengerResponse> createDriver(@Valid @Validated(OnCreate.class)
                                                       @RequestBody PassengerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerService.createPassenger(request));
    }

    @Operation(summary = "Update passenger by Id")
    @PutMapping("/{id}")
    public PassengerResponse updateDriver(@PathVariable int id,
                                       @Valid @Validated(OnUpdate.class) @RequestBody PassengerRequest request) {
        return passengerService.updatePassenger(id, request);
    }
}

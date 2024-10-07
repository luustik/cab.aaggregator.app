package cab.aggregator.app.passengerservice.controller;

import cab.aggregator.app.passengerservice.dto.request.PassengerRequest;
import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;
import cab.aggregator.app.passengerservice.dto.validation.OnCreate;
import cab.aggregator.app.passengerservice.dto.validation.OnUpdate;
import cab.aggregator.app.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerControllerImpl {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public PassengerResponse getPassengerById(@PathVariable int id) {
        return passengerService.getPassengerById(id);
    }

    @GetMapping
    public PassengerContainerResponse getAllPassengers(){
        return passengerService.getAllPassengers();
    }

    @GetMapping("/passenger-by-phone/{phone}")
    public PassengerResponse getPassengerByPhone(@Valid @Validated
                                                         @PathVariable String phone) {
        return passengerService.getPassengerByPhone(phone);
    }

    @GetMapping("/passenger-by-email/{email}")
    public PassengerResponse getPassengerByEmail(@Valid @Validated
                                                 @PathVariable String email) {
        return passengerService.getPassengerByEmail(email);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<PassengerResponse> softDeleteDriverById(@PathVariable int id) {
        passengerService.softDeletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> hardDeleteDriverById(@PathVariable int id) {
        passengerService.hardDeletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> createDriver(@Valid @Validated(OnCreate.class)
                                                       @RequestBody PassengerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerService.createPassenger(request));
    }

    @PutMapping("/{id}")
    public PassengerResponse updateDriver(@PathVariable int id,
                                       @Valid @Validated(OnUpdate.class) @RequestBody PassengerRequest request) {
        return passengerService.updatePassenger(id, request);
    }
}

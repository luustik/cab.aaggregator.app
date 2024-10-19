package cab.aggregator.app.rideservice.service.impl;

import cab.aggregator.app.rideservice.client.driver.DriverClientContainer;
import cab.aggregator.app.rideservice.client.passenger.PassengerClientContainer;
import cab.aggregator.app.rideservice.entity.Ride;
import cab.aggregator.app.rideservice.exception.EntityNotFoundException;
import cab.aggregator.app.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static cab.aggregator.app.rideservice.utility.Constants.ENTITY_WITH_ID_NOT_FOUND_MESSAGE;
import static cab.aggregator.app.rideservice.utility.ResourceName.RIDE;

@Component
@RequiredArgsConstructor
public class Validator {

    private final MessageSource messageSource;
    private final RideRepository rideRepository;
    private final DriverClientContainer driverClientContainer;
    private final PassengerClientContainer passengerClientContainer;

    public void checkIfExistPassenger(long passengerId) {
        passengerClientContainer.getById((int) passengerId);
    }

    public void checkIfExistDriver(long driverId) {
        driverClientContainer.getById((int) driverId);
    }

    public Ride findById(Long rideId) {
        return rideRepository
                .findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(ENTITY_WITH_ID_NOT_FOUND_MESSAGE,
                        new Object[]{RIDE, rideId}, Locale.getDefault())));
    }
}

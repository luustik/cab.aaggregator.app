package cab.aggregator.app.rideservice.service.impl;

import cab.aggregator.app.rideservice.client.driver.DriverClientContainer;
import cab.aggregator.app.rideservice.client.passenger.PassengerClientContainer;
import cab.aggregator.app.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    private final DriverClientContainer driverClientContainer;
    private final PassengerClientContainer passengerClientContainer;

    public void checkIfExistPassenger(long passengerId) {
        passengerClientContainer.getById((int) passengerId);
    }

    public void checkIfExistDriver(long driverId) {
        driverClientContainer.getById((int) driverId);
    }
}

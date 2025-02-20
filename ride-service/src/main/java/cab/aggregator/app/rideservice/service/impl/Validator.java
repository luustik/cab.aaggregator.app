package cab.aggregator.app.rideservice.service.impl;

import cab.aggregator.app.rideservice.client.driver.DriverClientContainer;
import cab.aggregator.app.rideservice.client.passenger.PassengerClientContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    private final DriverClientContainer driverClientContainer;
    private final PassengerClientContainer passengerClientContainer;

    public void checkIfExistPassenger(long passengerId, String authToken) {
        passengerClientContainer.getById((int) passengerId, authToken);
    }

    public void checkIfExistDriver(long driverId, String authToken) {
        driverClientContainer.getById((int) driverId, authToken);
    }
}

package cab.aggregator.app.ratingservice.service.impl;

import cab.aggregator.app.ratingservice.client.driver.DriverClientContainer;
import cab.aggregator.app.ratingservice.client.passenger.PassengerClientContainer;
import cab.aggregator.app.ratingservice.client.ride.RideClientContainer;
import cab.aggregator.app.ratingservice.entity.enums.UserRole;
import cab.aggregator.app.ratingservice.exception.ResourceAlreadyExistException;
import cab.aggregator.app.ratingservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import static cab.aggregator.app.ratingservice.utility.Constants.RATING;
import static cab.aggregator.app.ratingservice.utility.Constants.RESOURCE_ALREADY_EXISTS_MESSAGE;
import static cab.aggregator.app.ratingservice.utility.Constants.RIDE;

@Component
@RequiredArgsConstructor
public class Validator {

    private final DriverClientContainer driverClientContainer;
    private final PassengerClientContainer passengerClientContainer;
    private final RideClientContainer rideClientContainer;
    private final RatingRepository ratingRepository;
    private final MessageSource messageSource;

    public void checkIfExistUser(Long userId, UserRole role, String authToken) {
        switch (role) {
            case DRIVER -> checkIfExistDriver(userId, authToken);
            case PASSENGER -> checkIfExistPassenger(userId, authToken);
        }
    }

    public void checkIfExistRide(Long rideId, String authToken) {
        rideClientContainer.getById(rideId, authToken);
    }

    private void checkIfExistPassenger(Long passengerId, String authToken) {
        passengerClientContainer.getById(passengerId.intValue(), authToken);
    }

    private void checkIfExistDriver(Long driverId, String authToken) {
        driverClientContainer.getById(driverId.intValue(), authToken);
    }

    public void checkIfExistRatingByRideIdAndRole(Long rideId, UserRole role) {
        if (ratingRepository.existsByRideIdAndUserRole(rideId, role)) {
            throw new ResourceAlreadyExistException(messageSource.getMessage(RESOURCE_ALREADY_EXISTS_MESSAGE,
                    new Object[]{RATING, RIDE, rideId, role.toString()}, LocaleContextHolder.getLocale()));
        }
    }

}

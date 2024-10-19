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

    public void checkIfExistUser(Long userId, UserRole role) {
        switch (role) {
            case DRIVER -> checkIfExistDriver(userId);
            case PASSENGER -> checkIfExistPassenger(userId);
        }
    }

    public void checkIfExistRide(Long rideId) {
        rideClientContainer.getById(rideId);
    }

    private void checkIfExistPassenger(Long passengerId) {
        passengerClientContainer.getById(passengerId.intValue());
    }

    private void checkIfExistDriver(Long driverId) {
        driverClientContainer.getById(driverId.intValue());
    }

    public void checkIfExistRatingByRideIdAndRole(Long rideId, UserRole role) {
        if (ratingRepository.existsByRideIdAndUserRole(rideId, role)) {
            throw new ResourceAlreadyExistException(messageSource.getMessage(RESOURCE_ALREADY_EXISTS_MESSAGE,
                    new Object[]{RATING, RIDE, rideId, role.toString()}, LocaleContextHolder.getLocale()));
        }
    }

}

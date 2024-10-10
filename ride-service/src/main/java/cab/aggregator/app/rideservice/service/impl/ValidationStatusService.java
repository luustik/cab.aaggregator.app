package cab.aggregator.app.rideservice.service.impl;

import cab.aggregator.app.rideservice.entity.enums.Status;
import cab.aggregator.app.rideservice.exception.ImpossibleStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static cab.aggregator.app.rideservice.utility.Constants.VALIDATION_STATUS_FAILED_MESSAGE;

@Component
@RequiredArgsConstructor
public class ValidationStatusService {

    private final MessageSource messageSource;

    public Status validateStatus(Status currentStatus, Status substituteStatus) {
        switch (currentStatus) {
            case CREATED:
                if (substituteStatus == Status.ACCEPTED || substituteStatus == Status.CANCELLED) {
                    return substituteStatus;
                }
                throwExceptionValidStatus(substituteStatus);
            case ACCEPTED:
                if (substituteStatus == Status.WAY_TO_PASSENGER || substituteStatus == Status.CANCELLED) {
                    return substituteStatus;
                }
                throwExceptionValidStatus(substituteStatus);
            case WAY_TO_PASSENGER:
                if (substituteStatus == Status.WAY_TO_DESTINATION || substituteStatus == Status.CANCELLED) {
                    return substituteStatus;
                }
                throwExceptionValidStatus(substituteStatus);
            case WAY_TO_DESTINATION:
                if (substituteStatus == Status.COMPLETED || substituteStatus == Status.CANCELLED) {
                    return substituteStatus;
                }
                throwExceptionValidStatus(substituteStatus);
            case COMPLETED:
            case CANCELLED:
                throwExceptionValidStatus(substituteStatus);
        }
        return currentStatus;
    }

    public void throwExceptionValidStatus(Status substituteStatus) {
        throw new ImpossibleStatusException(messageSource
                .getMessage(VALIDATION_STATUS_FAILED_MESSAGE,
                        new Object[]{substituteStatus},
                        Locale.getDefault()));
    }
}

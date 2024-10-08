package cab.aggregator.app.rideservice.utility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessage {
    public static final String ENTITY_WITH_ID_NOT_FOUND_MESSAGE = "The %s with id %d not found";
    public static final String INTERNAL_ERROR_MESSAGE = "Internal error";
    public static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
}
package cab.aggregator.app.ratingservice.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String VALIDATION_FAILED_MESSAGE = "validation.failed.message";
    public static final String DEFAULT_EXCEPTION_MESSAGE = "default.exception.message";
    public static final String ENTITY_WITH_ID_NOT_FOUND_MESSAGE = "entity.not.found.message";
    public static final String ENTITY_WITH_RIDE_ID_NOT_FOUND_MESSAGE = "entity.resource.not.found.message";

    public static final String RATING = "Rating";
    public static final String RIDE = "Ride";
}
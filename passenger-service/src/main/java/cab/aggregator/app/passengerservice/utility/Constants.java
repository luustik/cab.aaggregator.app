package cab.aggregator.app.passengerservice.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String PASSENGER = "Passenger";

    public static final String ENTITY_WITH_ID_NOT_FOUND_MESSAGE = "entity.with.id.not.found.message";
    public static final String ENTITY_WITH_RESOURCE_NOT_FOUND_MESSAGE = "entity.with.resource.not.found.message";
    public static final String RESOURCE_ALREADY_EXIST_MESSAGE = "resource.already.exist.message";
    public static final String VALIDATION_FAILED_MESSAGE = "validation.failed.message";
    public static final String DEFAULT_EXCEPTION_MESSAGE = "default.exception.message";

    public static final String EMAIL_PATTERN = "^[\\w.-]+@[\\w.-]+\\.[\\w.-]+$";
    public static final String PHONE_NUMBER_PATTERN = "\\+375\\((29|44|33|25)\\)\\d{7}$";
}

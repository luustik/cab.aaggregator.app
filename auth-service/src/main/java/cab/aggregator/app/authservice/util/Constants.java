package cab.aggregator.app.authservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final String EMAIL_PATTERN = "^[\\w.-]+@[\\w.-]+\\.[\\w.-]+$";
    public static final String PHONE_NUMBER_PATTERN = "\\+375\\((29|44|33|25)\\)\\d{7}$";
    public static final String GENDER_PATTERN = "^(MALE|FEMALE)$";
    public static final String ROLE_PATTERN = "^(DRIVER|PASSENGER)$";

    public static final String MESSAGE = "message";

    public static final String VALIDATION_FAILED_MESSAGE = "validation.failed.message";
    public static final String DEFAULT_EXCEPTION_MESSAGE = "default.exception.message";
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "service.unavailable";

    public static final String PASSENGER_ROLE = "PASSENGER";
    public static final String DRIVER_ROLE = "DRIVER";
    public static final String AUTH_TOKEN = "Bearer ";
    public static final String GENDER_FIELD = "gender";

    public static final String GRANT_TYPE_FIELD = "grant_type";
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";
    public static final String CLIENT_ID_FIELD = "client_id";
    public static final String GRANT_TYPE_PASSWORD_FIELD = "password";
}

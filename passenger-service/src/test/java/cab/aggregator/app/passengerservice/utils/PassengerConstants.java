package cab.aggregator.app.passengerservice.utils;

import cab.aggregator.app.passengerservice.dto.request.PassengerRequest;
import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;
import cab.aggregator.app.passengerservice.entity.Passenger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerConstants {

    public static final int OFFSET = 0;
    public static final int LIMIT = 20;

    public static final String RESOURCE_ALREADY_EXISTS = "The %s with %s already exist";
    public static final String PASSENGER_RESOURCE = "Passenger";

    public static final Passenger PASSENGER = createPassenger();
    public static final int PASSENGER_ID = 1;
    public static final String PASSENGER_EMAIL = "osfbeobneo@knbeo.vneojb";
    public static final String PASSENGER_PHONE = "+375(44)5567853";
    public static final String PASSENGER_UPDATED_EMAIL = "11r@kfjbn.snb";
    public static final String PASSENGER_UPDATED_PHONE = "+375(29)1111111";
    public static final String PASSENGER_INVALID_PHONE = "qwe123";
    public static final String PASSENGER_INVALID_EMAIL = "qwe123";

    public static final PassengerRequest PASSENGER_UPDATED_REQUEST = createPassengerUpdatedRequest();
    public static final PassengerRequest PASSENGER_INVALID_REQUEST = new PassengerRequest("Kirill", PASSENGER_INVALID_PHONE, PASSENGER_INVALID_EMAIL);
    public static final PassengerRequest PASSENGER_REQUEST = createPassengerRequest();
    public static final PassengerResponse PASSENGER_RESPONSE = createPassengerResponse();

    public static final List<Passenger> PASSENGER_LIST = List.of(PASSENGER);
    public static final Page<Passenger> PASSENGER_PAGE = new PageImpl<>(PASSENGER_LIST, PageRequest.of(OFFSET, LIMIT), PASSENGER_LIST.size());
    public static final List<PassengerResponse> PASSENGER_RESPONSE_LIST = List.of(PASSENGER_RESPONSE);
    public static final Page<PassengerResponse> PASSENGER_RESPONSE_PAGE = new PageImpl<>(PASSENGER_RESPONSE_LIST, PageRequest.of(OFFSET, LIMIT), PASSENGER_RESPONSE_LIST.size());
    public static final PassengerContainerResponse PASSENGER_CONTAINER_RESPONSE = createPassengerContainerResponse();

    public static final String PASSENGERS_ID_URL = "/api/v1/passengers/{id}";
    public static final String PASSENGERS_PHONE_URL = "/api/v1/passengers/phone/{phone}";
    public static final String PASSENGERS_EMAIL_URL = "/api/v1/passengers/email/{email}";
    public static final String PASSENGERS_URL = "/api/v1/passengers";
    public static final String PASSENGERS_SAFE_ID_URL = "/api/v1/passengers/soft/{id}";
    public static final String PASSENGERS_ADMIN_URL = "/api/v1/passengers/admin";

    private static Passenger createPassenger() {
        Passenger driver = new Passenger();
        driver.setId(PASSENGER_ID);
        driver.setName("Pasha");
        driver.setEmail(PASSENGER_EMAIL);
        driver.setPhone(PASSENGER_PHONE);
        driver.setDeleted(false);
        driver.setAvgGrade(0.0);
        return driver;
    }

    private static PassengerResponse createPassengerResponse() {
        return new PassengerResponse(PASSENGER_ID, "Kirill", PASSENGER_EMAIL, PASSENGER_PHONE, false, 0.0);
    }

    private static PassengerRequest createPassengerRequest() {
        return new PassengerRequest("Kirill", PASSENGER_EMAIL, PASSENGER_PHONE);
    }

    private static PassengerRequest createPassengerUpdatedRequest() {
        return new PassengerRequest("Kirill", PASSENGER_UPDATED_EMAIL, PASSENGER_UPDATED_PHONE);
    }

    private static PassengerContainerResponse createPassengerContainerResponse() {
        return PassengerContainerResponse.builder()
                .items(PASSENGER_RESPONSE_PAGE.getContent())
                .currentPage(PASSENGER_RESPONSE_PAGE.getNumber())
                .sizePage(PASSENGER_RESPONSE_PAGE.getSize())
                .countPages(PASSENGER_RESPONSE_PAGE.getTotalPages())
                .totalElements(PASSENGER_RESPONSE_PAGE.getTotalElements())
                .build();
    }
}

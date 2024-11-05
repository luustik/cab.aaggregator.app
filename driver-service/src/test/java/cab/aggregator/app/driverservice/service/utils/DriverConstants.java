package cab.aggregator.app.driverservice.service.utils;

import cab.aggregator.app.driverservice.dto.request.DriverRequest;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;
import cab.aggregator.app.driverservice.entity.Driver;
import cab.aggregator.app.driverservice.entity.enums.Gender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverConstants {

    public static final int OFFSET = 0;
    public static final int LIMIT = 20;

    public static final String RESOURCE_ALREADY_EXISTS = "The %s with %s already exist";
    public static final String DRIVER_RESOURCE = "Driver";

    public static final Driver DRIVER = createDriver();
    public static final int DRIVER_ID = 1;
    public static final String DRIVER_EMAIL = "kkbhe@kfjbn.snb";
    public static final String DRIVER_PHONE_NUMBER = "+375(29)1234567";
    public static final String DRIVER_GENDER = "MALE";
    public static final String DRIVER_UPDATED_EMAIL = "11r@kfjbn.snb";
    public static final String DRIVER_UPDATED_PHONE_NUMBER = "+375(29)1111111";

    public static final DriverRequest DRIVER_UPDATED_REQUEST = createDriverUpdatedRequest();
    public static final DriverRequest DRIVER_REQUEST = createDriverRequest();
    public static final DriverResponse DRIVER_RESPONSE = createDriverResponse();

    public static final List<Driver> DRIVER_LIST = createDriverList();
    public static final Page<Driver> DRIVER_PAGE = new PageImpl<>(DRIVER_LIST, PageRequest.of(OFFSET, LIMIT), DRIVER_LIST.size());
    public static final List<DriverResponse> DRIVER_RESPONSE_LIST = createDriverResponseList();
    public static final Page<DriverResponse> DRIVER_RESPONSE_PAGE = new PageImpl<>(DRIVER_RESPONSE_LIST, PageRequest.of(OFFSET, LIMIT), DRIVER_RESPONSE_LIST.size());
    public static final DriverContainerResponse DRIVER_CONTAINER_RESPONSE = createDriverContainerResponse();

    private static Driver createDriver() {
        Driver driver = new Driver();
        driver.setId(DRIVER_ID);
        driver.setName("Pasha");
        driver.setEmail(DRIVER_EMAIL);
        driver.setPhoneNumber(DRIVER_PHONE_NUMBER);
        driver.setGender(Gender.MALE);
        driver.setDeleted(false);
        driver.setAvgGrade(0.0);
        return driver;
    }

    private static DriverResponse createDriverResponse() {
        return new DriverResponse(DRIVER_ID, "Pasha", DRIVER_EMAIL, DRIVER_PHONE_NUMBER, DRIVER_GENDER, false, 0.0);
    }

    private static DriverRequest createDriverRequest() {
        return new DriverRequest("Pasha", DRIVER_EMAIL, DRIVER_PHONE_NUMBER, DRIVER_GENDER);
    }

    private static DriverRequest createDriverUpdatedRequest() {
        return new DriverRequest("Pasha", DRIVER_UPDATED_EMAIL, DRIVER_UPDATED_PHONE_NUMBER, DRIVER_GENDER);
    }

    private static List<Driver> createDriverList() {
        List<Driver> driverList = new ArrayList<>();
        driverList.add(DRIVER);
        return driverList;
    }

    private static List<DriverResponse> createDriverResponseList() {
        List<DriverResponse> driverList = new ArrayList<>();
        driverList.add(DRIVER_RESPONSE);
        return driverList;
    }

    private static DriverContainerResponse createDriverContainerResponse() {
        return DriverContainerResponse.builder()
                .items(DRIVER_RESPONSE_PAGE.getContent())
                .currentPage(DRIVER_RESPONSE_PAGE.getNumber())
                .sizePage(DRIVER_RESPONSE_PAGE.getSize())
                .countPages(DRIVER_RESPONSE_PAGE.getTotalPages())
                .totalElements(DRIVER_RESPONSE_PAGE.getTotalElements())
                .build();
    }
}

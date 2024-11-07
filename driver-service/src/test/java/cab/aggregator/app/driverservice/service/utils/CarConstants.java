package cab.aggregator.app.driverservice.service.utils;

import cab.aggregator.app.driverservice.dto.request.CarRequest;
import cab.aggregator.app.driverservice.dto.response.CarContainerResponse;
import cab.aggregator.app.driverservice.dto.response.CarResponse;
import cab.aggregator.app.driverservice.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static cab.aggregator.app.driverservice.service.utils.DriverConstants.DRIVER;
import static cab.aggregator.app.driverservice.service.utils.DriverConstants.DRIVER_ID;
import static cab.aggregator.app.driverservice.service.utils.DriverConstants.LIMIT;
import static cab.aggregator.app.driverservice.service.utils.DriverConstants.OFFSET;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CarConstants {

    public static final Car CAR = createCar();
    public static final CarRequest CAR_REQUEST = createCarRequest();
    public static final CarRequest CAR_UPDATED_REQUEST = createCarAnotherRequest();
    public static final CarResponse CAR_RESPONSE = createCarResponse();
    public static final int CAR_ID = 1;
    public static final String CAR_NUMBER = "7930AB-7";
    public static final String CAR_RESOURCE = "Car";

    public static final List<Car> CAR_LIST = List.of(CAR);
    public static final Page<Car> CAR_PAGE = new PageImpl<>(CAR_LIST, PageRequest.of(OFFSET, LIMIT), CAR_LIST.size());
    public static final List<CarResponse> CAR_RESPONSE_LIST = List.of(CAR_RESPONSE);
    public static final Page<CarResponse> CAR_RESPONSE_PAGE = new PageImpl<>(CAR_RESPONSE_LIST, PageRequest.of(OFFSET, LIMIT), CAR_RESPONSE_LIST.size());
    public static final CarContainerResponse CAR_CONTAINER_RESPONSE = createCarContainerResponse();

    private static Car createCar() {
        Car car = new Car();
        car.setId(CAR_ID);
        car.setColor("red");
        car.setModel("audi");
        car.setCarNumber(CAR_NUMBER);
        car.setDriver(DRIVER);
        return car;
    }

    private static CarContainerResponse createCarContainerResponse() {
        return CarContainerResponse.builder()
                .items(CAR_RESPONSE_PAGE.getContent())
                .currentPage(CAR_RESPONSE_PAGE.getNumber())
                .sizePage(CAR_RESPONSE_PAGE.getSize())
                .countPages(CAR_RESPONSE_PAGE.getTotalPages())
                .totalElements(CAR_RESPONSE_PAGE.getTotalElements())
                .build();
    }

    private static CarResponse createCarResponse() {
        return new CarResponse(CAR_ID, "red", "audi", CAR_NUMBER, DRIVER_ID);
    }

    private static CarRequest createCarAnotherRequest() {
        return new CarRequest("audi", "1111AA-1", "red", DRIVER_ID);
    }

    private static CarRequest createCarRequest() {
        return new CarRequest("audi", CAR_NUMBER, "red", DRIVER_ID);
    }
}

package cab.aggregator.app.rideservice.client.driver;

import cab.aggregator.app.rideservice.dto.client.DriverResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DriverClientContainer {

    private final DriverClient driverClient;

    @CircuitBreaker(name = "driver-service-circuit-breaker", fallbackMethod = "fallback")
    public DriverResponse getById(int id) {
        return driverClient.getDriverById(id);
    }

    private void fallback(Exception ex) throws Exception {
        log.warn(ex.getMessage(), ex);
        throw  ex;
    }
}

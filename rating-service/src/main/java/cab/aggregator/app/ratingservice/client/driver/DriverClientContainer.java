package cab.aggregator.app.ratingservice.client.driver;

import cab.aggregator.app.ratingservice.dto.client.DriverResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DriverClientContainer {

    private final DriverClient driverClient;

    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallback")
    public DriverResponse getById(int id) {
        return driverClient.getDriverById(id);
    }

    private void fallback(Exception ex) throws Exception {
        log.warn(ex.getMessage(), ex);
        throw ex;
    }
}

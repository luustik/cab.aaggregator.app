package cab.aggregator.app.ratingservice.client.ride;

import cab.aggregator.app.ratingservice.dto.client.RideResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RideClientContainer {

    private final RideClient rideClient;

    @CircuitBreaker(name = "ride-service-circuit-breaker", fallbackMethod = "fallback")
    public RideResponse getById(long id) {
        return rideClient.getRideById(id);
    }

    private RideResponse fallback(long id, Throwable ex) throws Throwable {
        log.warn("Fallback triggered for ID: {}, Exception: {}", id, ex.getMessage(), ex);
        throw ex;
    }
}

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

    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallback")
    public RideResponse getById(long id) {
        return rideClient.getRideById(id);
    }

    private void fallback(Exception ex) throws Exception {
        log.warn(ex.getMessage(), ex);
        throw  ex;
    }
}

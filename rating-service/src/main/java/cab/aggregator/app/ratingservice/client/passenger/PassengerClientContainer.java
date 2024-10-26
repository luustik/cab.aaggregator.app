package cab.aggregator.app.ratingservice.client.passenger;

import cab.aggregator.app.ratingservice.dto.client.PassengerResponse;
import cab.aggregator.app.ratingservice.dto.exception.ExceptionDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassengerClientContainer {

    private final PassengerClient passengerClient;

    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallback")
    public PassengerResponse getById(int id) {
        return passengerClient.getPassengerById(id);
    }

    private PassengerResponse fallback(int id, Throwable ex) throws Throwable {
        log.warn("Fallback triggered for ID: {}, Exception: {}", id, ex.getMessage(), ex);
        throw ex;
    }
}

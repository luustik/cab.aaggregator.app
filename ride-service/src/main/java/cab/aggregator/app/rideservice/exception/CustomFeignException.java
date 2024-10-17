package cab.aggregator.app.rideservice.exception;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    public CustomFeignException(String message) {
        super(message);
    }
}

package cab.aggregator.app.rideservice.exception;

public class ImpossibleStatusException extends RuntimeException {

    public ImpossibleStatusException(String message) {
        super(String.format(message));
    }
}

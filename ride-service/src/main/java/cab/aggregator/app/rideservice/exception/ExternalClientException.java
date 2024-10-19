package cab.aggregator.app.rideservice.exception;

import cab.aggregator.app.rideservice.dto.client.ClientErrorResponse;
import lombok.Getter;

@Getter
public class ExternalClientException extends RuntimeException {

    private final ClientErrorResponse clientErrorResponse;

    public ExternalClientException(ClientErrorResponse clientErrorResponse) {
        this.clientErrorResponse = clientErrorResponse;
    }
}

package cab.aggregator.app.ratingservice.exception;

import cab.aggregator.app.ratingservice.dto.client.ClientErrorResponse;
import lombok.Getter;

@Getter
public class ExternalClientException extends RuntimeException {

    private final ClientErrorResponse clientErrorResponse;

    public ExternalClientException(ClientErrorResponse clientErrorResponse) {
        this.clientErrorResponse = clientErrorResponse;
    }
}

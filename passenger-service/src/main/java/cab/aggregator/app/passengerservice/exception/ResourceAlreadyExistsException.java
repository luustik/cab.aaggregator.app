package cab.aggregator.app.passengerservice.exception;

import cab.aggregator.app.passengerservice.utility.ExceptionMessage;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String entityName, String resource) {
        super(String.format(ExceptionMessage.RESOURCE_ALREADY_EXIST_MESSAGE, entityName, resource));
    }
}

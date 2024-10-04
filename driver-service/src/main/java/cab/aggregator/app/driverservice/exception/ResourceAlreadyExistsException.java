package cab.aggregator.app.driverservice.exception;

import cab.aggregator.app.driverservice.utility.ExceptionMessage;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String entityName, String resource1, String resource2) {
        super(String.format(ExceptionMessage.RESOURCES_ALREADY_EXIST_MESSAGE, entityName, resource1, resource2));
    }

    public ResourceAlreadyExistsException(String entityName, String resource) {
        super(String.format(ExceptionMessage.RESOURCE_ALREADY_EXIST_MESSAGE, entityName, resource));
    }
}

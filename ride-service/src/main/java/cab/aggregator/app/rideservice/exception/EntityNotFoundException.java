package cab.aggregator.app.rideservice.exception;


import cab.aggregator.app.rideservice.utility.ExceptionMessage;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format(ExceptionMessage.ENTITY_WITH_ID_NOT_FOUND_MESSAGE, entityName, id));
    }

    public EntityNotFoundException(String entityName) {
        super(String.format(ExceptionMessage.ENTITY_LIST_IS_EMPTY_MESSAGE, entityName));
    }
}

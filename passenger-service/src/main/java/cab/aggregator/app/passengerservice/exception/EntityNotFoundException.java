package cab.aggregator.app.passengerservice.exception;

import cab.aggregator.app.passengerservice.utility.ExceptionMessage;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName, Integer id){
        super(String.format(ExceptionMessage.ENTITY_WITH_ID_NOT_FOUND_MESSAGE, entityName, id));
    }

    public EntityNotFoundException(String entityName, String resource){
        super(String.format(ExceptionMessage.ENTITY_WITH_RESOURCE_NOT_FOUND_MESSAGE, entityName, resource));
    }

}
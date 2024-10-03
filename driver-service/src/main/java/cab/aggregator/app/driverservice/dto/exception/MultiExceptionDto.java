package cab.aggregator.app.driverservice.dto.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record MultiExceptionDto (
    Integer status,

    String message,

    Map<String, String> errors
){
}

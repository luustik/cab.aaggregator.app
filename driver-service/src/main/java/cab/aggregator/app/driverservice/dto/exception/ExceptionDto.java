package cab.aggregator.app.driverservice.dto.exception;

import lombok.Builder;

@Builder
public record ExceptionDto(

        Integer status,

        String message
) {
}

package cab.aggregator.app.passengerservice.dto.exception;

import lombok.Builder;

@Builder
public record Exception(

        String message
) {
}

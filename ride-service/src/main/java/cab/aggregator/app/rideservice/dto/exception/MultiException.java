package cab.aggregator.app.rideservice.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Map;

@Builder
@Schema(description = "Multi exception DTO")
public record MultiException(

        @Schema(description = "Message")
        String message,

        @Schema(description = "Errors")
        Map<String, String> errors
) {
}

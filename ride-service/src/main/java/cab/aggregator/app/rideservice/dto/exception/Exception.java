package cab.aggregator.app.rideservice.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Exception DTO")
public record Exception(

        @Schema(description = "Message")
        String message
) {
}

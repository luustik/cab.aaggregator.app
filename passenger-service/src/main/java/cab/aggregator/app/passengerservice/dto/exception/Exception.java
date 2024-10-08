package cab.aggregator.app.passengerservice.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Exception DTO")
@Builder
public record Exception(

        @Schema(description = "Message")
        String message
) {
}

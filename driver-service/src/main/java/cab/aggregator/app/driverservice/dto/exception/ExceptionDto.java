package cab.aggregator.app.driverservice.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Exception DTO")
@Builder
public record ExceptionDto(

        @Schema(description = "Status")
        Integer status,

        @Schema(description = "Message")
        String message
) {
}

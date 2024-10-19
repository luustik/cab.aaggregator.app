package cab.aggregator.app.ratingservice.dto.client;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ClientErrorResponse(

        HttpStatus status,

        String message
) {
}

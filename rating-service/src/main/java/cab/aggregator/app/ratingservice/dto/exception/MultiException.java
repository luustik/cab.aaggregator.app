package cab.aggregator.app.ratingservice.dto.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record MultiException(

        String message,

        Map<String, String> errors
) {
}

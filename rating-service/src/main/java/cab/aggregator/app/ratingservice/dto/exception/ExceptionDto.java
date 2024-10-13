package cab.aggregator.app.ratingservice.dto.exception;

import lombok.Builder;

@Builder
public record ExceptionDto(

        String message
) {
}


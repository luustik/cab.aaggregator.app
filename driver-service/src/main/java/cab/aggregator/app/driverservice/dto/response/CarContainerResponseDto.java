package cab.aggregator.app.driverservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CarContainerResponseDto(
        List<CarResponseDto> carResponseDtos
) {
}

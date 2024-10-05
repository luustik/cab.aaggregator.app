package cab.aggregator.app.driverservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DriverContainerResponseDto(
        List<DriverResponseDto> driverResponseDtos
) {
}

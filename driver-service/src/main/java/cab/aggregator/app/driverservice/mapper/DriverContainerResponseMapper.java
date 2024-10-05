package cab.aggregator.app.driverservice.mapper;

import cab.aggregator.app.driverservice.dto.response.CarContainerResponseDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import cab.aggregator.app.driverservice.dto.response.DriverContainerResponseDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverContainerResponseMapper {

    default DriverContainerResponseDto toDto(List<DriverResponseDto> drivers){
        return DriverContainerResponseDto.builder().driverResponseDtos(drivers).build();
    }
}

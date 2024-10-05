package cab.aggregator.app.driverservice.mapper;

import cab.aggregator.app.driverservice.dto.response.CarContainerResponseDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarContainerResponseMapper {

    default CarContainerResponseDto toDto(List<CarResponseDto> cars){
        return CarContainerResponseDto.builder().carResponseDtos(cars).build();
    }
}

package cab.aggregator.app.driverservice.mapper;

import cab.aggregator.app.driverservice.dto.response.CarContainerResponse;
import cab.aggregator.app.driverservice.dto.response.CarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarContainerResponseMapper {

    default CarContainerResponse toDto(List<CarResponse> cars){
        return CarContainerResponse.builder().items(cars).build();
    }
}

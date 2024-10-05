package cab.aggregator.app.driverservice.mapper;

import cab.aggregator.app.driverservice.dto.response.DriverContainerResponse;
import cab.aggregator.app.driverservice.dto.response.DriverResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverContainerResponseMapper {

    default DriverContainerResponse toDto(List<DriverResponse> drivers){
        return DriverContainerResponse.builder().items(drivers).build();
    }
}

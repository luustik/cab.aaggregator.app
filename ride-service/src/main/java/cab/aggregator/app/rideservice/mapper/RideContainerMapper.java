package cab.aggregator.app.rideservice.mapper;

import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideContainerMapper {

    default RideContainerResponse toContainer(List<RideResponse> rides){
        return RideContainerResponse.builder()
                .items(rides)
                .build();
    }
}

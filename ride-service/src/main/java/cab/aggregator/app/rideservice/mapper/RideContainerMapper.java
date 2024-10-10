package cab.aggregator.app.rideservice.mapper;

import cab.aggregator.app.rideservice.dto.response.RideContainerResponse;
import cab.aggregator.app.rideservice.dto.response.RideResponse;
import cab.aggregator.app.rideservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideContainerMapper {

    default RideContainerResponse toContainer(Page<RideResponse> rides){
        return RideContainerResponse.builder()
                .items(rides)
                .build();
    }
}

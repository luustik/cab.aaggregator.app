package cab.aggregator.app.rideservice.dto.response;

import cab.aggregator.app.rideservice.entity.Ride;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record RideContainerResponse(

    Page<RideResponse> items
){
}

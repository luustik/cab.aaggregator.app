package cab.aggregator.app.rideservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RideContainerResponse(

    List<RideResponse> items
){
}

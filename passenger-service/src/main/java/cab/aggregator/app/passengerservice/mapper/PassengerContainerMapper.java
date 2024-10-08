package cab.aggregator.app.passengerservice.mapper;

import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerContainerMapper {

        default PassengerContainerResponse toContainer(List<PassengerResponse> passengers){
            return PassengerContainerResponse.builder().items(passengers).build();
        }

}

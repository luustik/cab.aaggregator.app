package cab.aggregator.app.driverservice.mapper;


import cab.aggregator.app.driverservice.dto.request.CarRequestDto;
import cab.aggregator.app.driverservice.dto.response.CarResponseDto;
import cab.aggregator.app.driverservice.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarResponseDto toDto(Car car);

    void updateCarFromDto(CarRequestDto carRequestDto, @MappingTarget Car
            car);

    List<CarResponseDto> toDtoList(List<Car> cars);

    Car toEntity(CarRequestDto carRequestDto);

}

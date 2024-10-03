package cab.aggregator.app.driverservice.mapper;

import cab.aggregator.app.driverservice.dto.request.DriverRequestDto;
import cab.aggregator.app.driverservice.dto.response.DriverResponseDto;
import cab.aggregator.app.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverResponseDto toDto(Driver driver);

    void updateDriverFromDto(DriverRequestDto driverRequestDto, @MappingTarget Driver
            driver);

    List<DriverResponseDto> toDtoList(List<Driver> drivers);

    Driver toEntity(DriverRequestDto driverRequestDto);

}

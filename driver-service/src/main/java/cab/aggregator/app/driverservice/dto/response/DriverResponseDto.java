package cab.aggregator.app.driverservice.dto.response;

import java.util.Set;

public record DriverResponseDto(

         int id,

         String name,

         String email,

         String phoneNumber,

         String gender,

         boolean deleted
) {
}

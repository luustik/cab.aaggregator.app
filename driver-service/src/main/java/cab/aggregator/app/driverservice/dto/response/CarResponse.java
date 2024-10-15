package cab.aggregator.app.driverservice.dto.response;

public record CarResponse(

        int id,

        String color,

        String model,

        String carNumber,

        Integer driverId

) {
}

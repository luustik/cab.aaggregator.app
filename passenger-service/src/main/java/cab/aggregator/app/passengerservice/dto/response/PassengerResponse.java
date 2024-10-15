package cab.aggregator.app.passengerservice.dto.response;

public record PassengerResponse(
        int id,

        String name,

        String email,

        String phone,

        boolean deleted
) {
}

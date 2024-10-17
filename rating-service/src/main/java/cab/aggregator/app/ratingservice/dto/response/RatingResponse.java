package cab.aggregator.app.ratingservice.dto.response;

public record RatingResponse(

        Long id,

        Long rideId,

        Long userId,

        Integer rating,

        String comment,

        String userRole
) {
}

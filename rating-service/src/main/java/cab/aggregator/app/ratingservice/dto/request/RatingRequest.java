package cab.aggregator.app.ratingservice.dto.request;

public record RatingRequest (

        Long rideId,

        Long userId,

        Integer rating,

        String comment,

        String roleUser
){
}

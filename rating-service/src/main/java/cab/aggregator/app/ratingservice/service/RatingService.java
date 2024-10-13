package cab.aggregator.app.ratingservice.service;

import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;

public interface RatingService {

    RatingResponse getRatingById(Long id);

    RatingResponse getRatingByRideId(Long rideId);

    RatingContainerResponse getAllRatings(int offset, int limit);

    RatingContainerResponse getAllByUserIdAndRole(Long userId, String role, int offset, int limit);

    RatingContainerResponse getAllByRole(String role, int offset, int limit);

    void deleteRating(Long id);

    RatingResponse createRating(RatingRequest ratingRequest);

    RatingResponse updateRating(Long id,RatingRequest ratingRequest);
}

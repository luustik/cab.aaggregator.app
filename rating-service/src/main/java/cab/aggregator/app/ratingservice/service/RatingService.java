package cab.aggregator.app.ratingservice.service;

import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.request.RatingUpdateDto;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;

public interface RatingService {

    RatingResponse getRatingById(Long id);

    RatingResponse getRatingByRideIdAndRole(Long rideId, String role);

    RatingContainerResponse getAllByUserIdAndRole(Long userId, String role, int offset, int limit);

    RatingContainerResponse getAllByRole(String role, int offset, int limit);

    RatingContainerResponse getAllRatings(int offset, int limit);

    void deleteRating(Long id);

    RatingResponse createRating(RatingRequest ratingRequest);

    RatingResponse updateRating(Long id, RatingUpdateDto ratingUpdateDto);
}

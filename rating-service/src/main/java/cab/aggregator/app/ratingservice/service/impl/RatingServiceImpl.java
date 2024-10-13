package cab.aggregator.app.ratingservice.service.impl;

import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;
import cab.aggregator.app.ratingservice.service.RatingService;

public class RatingServiceImpl implements RatingService {
    @Override
    public RatingResponse getRatingById(Long id) {
        return null;
    }

    @Override
    public RatingResponse getRatingByRideId(Long rideId) {
        return null;
    }

    @Override
    public RatingContainerResponse getAllRatings(int offset, int limit) {
        return null;
    }

    @Override
    public RatingContainerResponse getAllByUserIdAndRole(Long userId, String role, int offset, int limit) {
        return null;
    }

    @Override
    public RatingContainerResponse getAllByRole(String role, int offset, int limit) {
        return null;
    }

    @Override
    public void deleteRating(Long id) {

    }

    @Override
    public RatingResponse createRating(RatingRequest ratingRequest) {
        return null;
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest ratingRequest) {
        return null;
    }
}

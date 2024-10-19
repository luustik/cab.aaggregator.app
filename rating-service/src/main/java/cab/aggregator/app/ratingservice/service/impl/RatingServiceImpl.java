package cab.aggregator.app.ratingservice.service.impl;

import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.request.RatingUpdateDto;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;
import cab.aggregator.app.ratingservice.entity.Rating;
import cab.aggregator.app.ratingservice.entity.enums.UserRole;
import cab.aggregator.app.ratingservice.mapper.RatingContainerMapper;
import cab.aggregator.app.ratingservice.mapper.RatingMapper;
import cab.aggregator.app.ratingservice.repository.RatingRepository;
import cab.aggregator.app.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RatingContainerMapper ratingContainerMapper;
    private final Validator validator;

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingById(Long id) {
        return ratingMapper
                .toDto(validator.findRatingById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingByRideIdAndRole(Long rideId, String role) {
        validator.checkIfExistRide(rideId);
        return ratingMapper
                .toDto(validator.findRatingByRideIdAndRole(rideId, role));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingContainerResponse getAllRatings(int offset, int limit) {
        return ratingContainerMapper
                .toContainer(ratingRepository
                        .findAll(PageRequest.of(offset, limit))
                        .map(ratingMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingContainerResponse getAllByUserIdAndRole(Long userId, String role, int offset, int limit) {
        validator.checkIfExistUser(userId, UserRole.valueOf(role.toUpperCase()));
        return ratingContainerMapper
                .toContainer(ratingRepository
                        .findAllByUserIdAndUserRole(userId, UserRole.valueOf(role.toUpperCase())
                                , PageRequest.of(offset, limit))
                        .map(ratingMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingContainerResponse getAllByRole(String role, int offset, int limit) {
        return ratingContainerMapper
                .toContainer(ratingRepository
                        .findAllByUserRole(UserRole.valueOf(role.toUpperCase()), PageRequest.of(offset, limit))
                        .map(ratingMapper::toDto));
    }

    @Override
    @Transactional
    public void deleteRating(Long id) {
        ratingRepository.delete(validator.findRatingById(id));
    }

    @Override
    @Transactional
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Rating rating = ratingMapper.toEntity(ratingRequest);
        validator.checkIfExistUser(rating.getUserId(), rating.getUserRole());
        validator.checkIfExistRide(rating.getRideId());
        validator.checkIfExistRatingByRideIdAndRole(rating.getRideId(), rating.getUserRole());
        ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

    @Override
    @Transactional
    public RatingResponse updateRating(Long id, RatingUpdateDto ratingUpdateDto) {
        Rating rating = validator.findRatingById(id);
        rating.setRating(ratingUpdateDto.rating());
        rating.setComment(ratingUpdateDto.comment());
        ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

}

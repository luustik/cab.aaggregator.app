package cab.aggregator.app.ratingservice.service.impl;

import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;
import cab.aggregator.app.ratingservice.entity.Rating;
import cab.aggregator.app.ratingservice.entity.enums.RoleUser;
import cab.aggregator.app.ratingservice.exception.EntityNotFoundException;
import cab.aggregator.app.ratingservice.exception.ResourceAlreadyExistException;
import cab.aggregator.app.ratingservice.mapper.RatingContainerMapper;
import cab.aggregator.app.ratingservice.mapper.RatingMapper;
import cab.aggregator.app.ratingservice.repository.RatingRepository;
import cab.aggregator.app.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static cab.aggregator.app.ratingservice.utility.Constants.*;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MessageSource messageSource;
    private final RatingMapper ratingMapper;
    private final RatingContainerMapper ratingContainerMapper;

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingById(Long id) {
        return ratingMapper
                .toDto(findRatingById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingByRideIdAndRole(Long rideId, String role) {
        return ratingMapper
                .toDto(findRatingByRideIdAndRole(rideId, role));
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
        return ratingContainerMapper
                .toContainer(ratingRepository
                        .findAllByUserIdAndRoleUser(userId, RoleUser.valueOf(role.toUpperCase())
                                , PageRequest.of(offset, limit))
                        .map(ratingMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingContainerResponse getAllByRole(String role, int offset, int limit) {
        return ratingContainerMapper
                .toContainer(ratingRepository
                        .findAllByRoleUser(RoleUser.valueOf(role.toUpperCase()), PageRequest.of(offset, limit))
                        .map(ratingMapper::toDto));
    }

    @Override
    @Transactional
    public void deleteRating(Long id) {
        ratingRepository.delete(findRatingById(id));
    }

    @Override
    @Transactional
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Rating rating = ratingMapper.toEntity(ratingRequest);
        checkIfExistRatingByRideIdAndRole(rating.getRideId(), rating.getRoleUser());
        ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

    //!RoleUser.valueOf(ratingRequest.roleUser()).equals(rating.getRoleUser())
    @Override
    @Transactional
    public RatingResponse updateRating(Long id, RatingRequest ratingRequest) {
        Rating rating = findRatingById(id);
        if (!ratingRequest.rideId().equals(rating.getRideId())
                || !RoleUser.valueOf(ratingRequest.roleUser()).equals(rating.getRoleUser())) {
            checkIfExistRatingByRideIdAndRole(ratingRequest.rideId(), RoleUser.valueOf(ratingRequest.roleUser().toUpperCase()));
        }
        ratingMapper.updateRatingFromDto(ratingRequest, rating);
        ratingRepository.save(rating);
        return ratingMapper.toDto(rating);
    }

    private void checkIfExistRatingByRideIdAndRole(Long rideId, RoleUser role) {
        if (ratingRepository.existsByRideIdAndRoleUser(rideId, role)) {
            throw new ResourceAlreadyExistException(messageSource.getMessage(RESOURCE_ALREADY_EXISTS_MESSAGE,
                    new Object[]{RATING, RIDE, rideId, role.toString()}, Locale.getDefault()));
        }
    }

    private Rating findRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(ENTITY_WITH_ID_NOT_FOUND_MESSAGE,
                        new Object[]{RATING, id}, Locale.getDefault())));
    }

    private Rating findRatingByRideIdAndRole(Long rideId, String role) {
        return ratingRepository.findByRideIdAndRoleUser(rideId, RoleUser.valueOf(role.toUpperCase()))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(ENTITY_RESOURCE_NOT_FOUND_MESSAGE,
                        new Object[]{role, RATING, RIDE, rideId}, Locale.getDefault())));
    }
}

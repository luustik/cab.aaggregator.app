package cab.aggregator.app.ratingservice.service.impl;

import cab.aggregator.app.ratingservice.dto.kafka.AvgRatingUserResponse;
import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.request.RatingUpdateDto;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;
import cab.aggregator.app.ratingservice.entity.Rating;
import cab.aggregator.app.ratingservice.entity.enums.UserRole;
import cab.aggregator.app.ratingservice.exception.EmptyListException;
import cab.aggregator.app.ratingservice.exception.EntityNotFoundException;
import cab.aggregator.app.ratingservice.kafka.KafkaSender;
import cab.aggregator.app.ratingservice.mapper.RatingContainerMapper;
import cab.aggregator.app.ratingservice.mapper.RatingMapper;
import cab.aggregator.app.ratingservice.repository.RatingRepository;
import cab.aggregator.app.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cab.aggregator.app.ratingservice.utility.Constants.ENTITY_RESOURCE_NOT_FOUND_MESSAGE;
import static cab.aggregator.app.ratingservice.utility.Constants.ENTITY_WITH_ID_NOT_FOUND_MESSAGE;
import static cab.aggregator.app.ratingservice.utility.Constants.LIST_EMPTY_MESSAGE;
import static cab.aggregator.app.ratingservice.utility.Constants.RATING;
import static cab.aggregator.app.ratingservice.utility.Constants.RIDE;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final MessageSource messageSource;
    private final RatingContainerMapper ratingContainerMapper;
    private final Validator validator;
    private final KafkaSender kafkaSender;

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingById(Long id) {
        return ratingMapper
                .toDto(findRatingById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getRatingByRideIdAndRole(Long rideId, String role) {
        validator.checkIfExistRide(rideId);
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
        Rating rating = findRatingById(id);
        ratingRepository.delete(rating);
        AvgRatingUserResponse avgRatingUserResponse = calculateAvgRating(rating.getUserId(), rating.getUserRole().name());
        sendUserAvgRating(avgRatingUserResponse, rating.getUserRole());
    }

    @Override
    @Transactional
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Rating rating = ratingMapper.toEntity(ratingRequest);
        validator.checkIfExistUser(rating.getUserId(), rating.getUserRole());
        validator.checkIfExistRide(rating.getRideId());
        validator.checkIfExistRatingByRideIdAndRole(rating.getRideId(), rating.getUserRole());
        ratingRepository.save(rating);
        AvgRatingUserResponse avgRatingUserResponse = calculateAvgRating(rating.getUserId(), rating.getUserRole().name());
        sendUserAvgRating(avgRatingUserResponse, rating.getUserRole());
        return ratingMapper.toDto(rating);
    }

    @Override
    @Transactional
    public RatingResponse updateRating(Long id, RatingUpdateDto ratingUpdateDto) {
        Rating rating = findRatingById(id);
        rating.setRating(ratingUpdateDto.rating());
        rating.setComment(ratingUpdateDto.comment());
        ratingRepository.save(rating);
        AvgRatingUserResponse avgRatingUserResponse = calculateAvgRating(rating.getUserId(), rating.getUserRole().name());
        sendUserAvgRating(avgRatingUserResponse, rating.getUserRole());
        return ratingMapper.toDto(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public AvgRatingUserResponse calculateRating(Long id, String userRole) {
        validator.checkIfExistUser(id, UserRole.valueOf(userRole.toUpperCase()));
        AvgRatingUserResponse avgRatingUserResponse = calculateAvgRating(id, userRole);
        sendUserAvgRating(avgRatingUserResponse, UserRole.valueOf(userRole.toUpperCase()));
        return avgRatingUserResponse;
    }

    private AvgRatingUserResponse calculateAvgRating (Long id, String userRole) {
        List<Rating> userRatings = ratingRepository.findAllByUserIdAndUserRole(id, UserRole.valueOf(userRole.toUpperCase()));
        if(userRatings == null || userRatings.isEmpty()){
            throw new EmptyListException(messageSource.getMessage(LIST_EMPTY_MESSAGE,
                    new Object[]{RATING}, LocaleContextHolder.getLocale()));
        }
        double avgRating = userRatings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);

        return new AvgRatingUserResponse(id.intValue(), avgRating);
    }

    private void sendUserAvgRating(AvgRatingUserResponse avgRatingUserResponse, UserRole userRole) {
        switch (userRole) {
            case DRIVER -> kafkaSender.sendAvgRatingDriver(avgRatingUserResponse);
            case PASSENGER -> kafkaSender.sendAvgRatingPassenger(avgRatingUserResponse);
        }
    }

    private Rating findRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(ENTITY_WITH_ID_NOT_FOUND_MESSAGE,
                        new Object[]{RATING, id}, LocaleContextHolder.getLocale())));
    }

    private Rating findRatingByRideIdAndRole(Long rideId, String role) {
        return ratingRepository.findByRideIdAndUserRole(rideId, UserRole.valueOf(role.toUpperCase()))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(ENTITY_RESOURCE_NOT_FOUND_MESSAGE,
                        new Object[]{role, RATING, RIDE, rideId}, LocaleContextHolder.getLocale())));
    }

}

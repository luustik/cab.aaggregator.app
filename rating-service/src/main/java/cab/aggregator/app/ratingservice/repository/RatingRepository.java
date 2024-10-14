package cab.aggregator.app.ratingservice.repository;

import cab.aggregator.app.ratingservice.entity.Rating;
import cab.aggregator.app.ratingservice.entity.enums.RoleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findAllByUserIdAndRoleUser(Long userId, RoleUser roleUser, Pageable pageable);

    Page<Rating> findAllByRoleUser(RoleUser roleUser, Pageable pageable);

    Optional<Rating> findByRideId(Long rideId);
}

package cab.aggregator.app.rideservice.repository;

import cab.aggregator.app.rideservice.entity.Ride;
import cab.aggregator.app.rideservice.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findAllByStatus(Status status);

    List<Ride> findAllByDriverId(Long driverId);

    List<Ride> findAllByPassengerId(Long passengerId);

    List<Ride> findAllByOrderDateTimeBetween(LocalDateTime start, LocalDateTime end);
}

package cab.aggregator.app.passengerservice.repository;

import cab.aggregator.app.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    Optional<Passenger> findById(int passengerId);

    Optional<Passenger> findByEmail(String email);

    Optional<Passenger> findByPhone(String phone);

    List<Passenger> findAllByDeletedIsFalse();

    List<Passenger> findAllByDeletedIsTrue();

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}

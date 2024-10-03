package cab.aggregator.app.driverservice.repository;

import cab.aggregator.app.driverservice.entity.Driver;
import cab.aggregator.app.driverservice.entity.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findById(int driverId);

    List<Driver> findAllByGender(Gender gender);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String driverPhoneNumber);
}

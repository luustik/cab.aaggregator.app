package cab.aggregator.app.driverservice.repository;

import cab.aggregator.app.driverservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findById(int driverId);

    Optional<Driver> findByName(String driverName);

    Optional<Driver> findByPhoneNumber(String driverPhoneNumber);

    boolean existsById(int driverId);

    boolean existsByName(String driverName);

    boolean existsByPhoneNumber(String driverPhoneNumber);
}

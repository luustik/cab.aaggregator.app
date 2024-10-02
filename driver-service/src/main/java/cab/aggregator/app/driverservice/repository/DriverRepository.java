package cab.aggregator.app.driverservice.repository;

import cab.aggregator.app.driverservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findByDriverId(int driverId);

    Optional<Driver> findByDriverName(String driverName);

    Optional<Driver> findByDriverPhoneNumber(String driverPhoneNumber);

    boolean existsByDriverId(int driverId);

    boolean existsByDriverName(String driverName);

    boolean existsByDriverPhoneNumber(String driverPhoneNumber);
}

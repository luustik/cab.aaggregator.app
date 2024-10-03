package cab.aggregator.app.driverservice.repository;

import cab.aggregator.app.driverservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findByModel(String model);

    Optional<Car> findByCarNumber(String carNumber);
}

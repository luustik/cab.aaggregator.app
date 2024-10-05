package cab.aggregator.app.driverservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "car_number", nullable = false, unique = true)
    private String carNumber;

    @ManyToOne
    @JoinColumn(name="driver_id")
    private Driver driver;
}

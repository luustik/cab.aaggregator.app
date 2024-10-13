package cab.aggregator.app.ratingservice.entity;

import cab.aggregator.app.ratingservice.entity.enums.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ride_id",nullable = false)
    private Long rideId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "role_user", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;
}

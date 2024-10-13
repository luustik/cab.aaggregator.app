package cab.aggregator.app.ratingservice.controller.impl;


import cab.aggregator.app.ratingservice.controller.RatingAPI;
import cab.aggregator.app.ratingservice.dto.request.RatingRequest;
import cab.aggregator.app.ratingservice.dto.response.RatingContainerResponse;
import cab.aggregator.app.ratingservice.dto.response.RatingResponse;
import cab.aggregator.app.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController implements RatingAPI {

    private final RatingService ratingService;

    @GetMapping("/{id}")
    public RatingResponse getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/ride-id/{rideId}")
    public RatingResponse getRatingByRideId(@PathVariable Long rideId) {
        return ratingService.getRatingById(rideId);
    }

    @GetMapping
    public RatingContainerResponse getAllRatings(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "limit", defaultValue = "20")int limit) {
        return ratingService.getAllRatings(offset, limit);
    }

    @GetMapping("/role/{role}")
    public RatingContainerResponse getAllRatingsByRole(@PathVariable String role,
                                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                       @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return ratingService.getAllByRole(role, offset, limit);
    }

    @GetMapping("/{role}/{userId}")
    public RatingContainerResponse getAllRatingsByUserIdAndRole(@PathVariable String role,
                                                       @PathVariable Long userId,
                                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                       @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return ratingService.getAllByUserIdAndRole(userId, role, offset, limit);
    }

    @DeleteMapping("/{id}")
    public void deleteRatingById(@PathVariable Long id) {
        ratingService.deleteRating(id);
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingService
                        .createRating(request));
    }

    @PutMapping("/{id}")
    public RatingResponse updateRating(@PathVariable Long id, @RequestBody RatingRequest request) {
        return ratingService.updateRating(id, request);
    }
}

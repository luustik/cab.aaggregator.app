package cab.aggregator.app.ratingservice.client.passenger;

import cab.aggregator.app.ratingservice.dto.client.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Profile("default")
@FeignClient(name = "${feign.client.name.passenger}", path = "${feign.client.path.passenger}")
public interface PassengerClient {

    @GetMapping("/{id}")
    PassengerResponse getPassengerById(@PathVariable int id, @RequestHeader("Authorization") String authorization);
}

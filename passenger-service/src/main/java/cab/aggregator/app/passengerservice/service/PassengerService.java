package cab.aggregator.app.passengerservice.service;

import cab.aggregator.app.passengerservice.dto.request.PassengerRequest;
import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {

    PassengerResponse getPassengerById(int id);

    PassengerContainerResponse getAllPassengers();

    PassengerResponse getPassengerByPhone(String phone);

    PassengerResponse getPassengerByEmail(String email);

    void softDeletePassenger(int id);

    void hardDeletePassenger(int id);

    PassengerResponse createPassenger(PassengerRequest passengerRequestDto);

    PassengerResponse updatePassenger(int id, PassengerRequest driverRequestDto);
}

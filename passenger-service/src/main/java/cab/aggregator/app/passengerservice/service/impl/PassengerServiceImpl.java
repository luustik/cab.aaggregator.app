package cab.aggregator.app.passengerservice.service.impl;

import cab.aggregator.app.passengerservice.dto.request.PassengerRequest;
import cab.aggregator.app.passengerservice.dto.response.PassengerContainerResponse;
import cab.aggregator.app.passengerservice.dto.response.PassengerResponse;
import cab.aggregator.app.passengerservice.entity.Passenger;
import cab.aggregator.app.passengerservice.exception.EntityNotFoundException;
import cab.aggregator.app.passengerservice.exception.ResourceAlreadyExistsException;
import cab.aggregator.app.passengerservice.mapper.PassengerContainerMapper;
import cab.aggregator.app.passengerservice.mapper.PassengerMapper;
import cab.aggregator.app.passengerservice.repository.PassengerRepository;
import cab.aggregator.app.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cab.aggregator.app.passengerservice.utility.ResourceName.PASSENGER;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerContainerMapper passengerContainerMapper;

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerById(int id) {
        return passengerMapper.toDto(findPassengerById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerContainerResponse getAllPassengers() {
        return passengerContainerMapper.toContainer(passengerMapper.toDtoList(passengerRepository.findAll()));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerByPhone(String email) {
        return passengerMapper.toDto(findPassengerByPhone(email));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerByEmail(String email) {
        return passengerMapper.toDto(findPassengerByEmail(email));
    }

    @Override
    @Transactional
    public void softDeletePassenger(int id) {
        Passenger passenger = findPassengerById(id);
        passenger.setDeleted(true);
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void hardDeletePassenger(int id) {
        Passenger passenger = findPassengerById(id);
        passengerRepository.delete(passenger);
    }

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerRequest passengerRequestDto) {
        Passenger passenger = checkIfPassengerDelete(passengerRequestDto);
        if(passenger != null) {
            passenger.setDeleted(false);
            passengerRepository.save(passenger);
            return passengerMapper.toDto(passenger);
        }
        checkIfPassengerUnique(passengerRequestDto);
        passenger = passengerMapper.toEntity(passengerRequestDto);
        passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    @Transactional
    public PassengerResponse updatePassenger(int id, PassengerRequest passengerRequestDto) {
        Passenger passenger = findPassengerById(id);
        if (!passengerRequestDto.email().equals(passenger.getEmail())) {
            checkIfEmailUnique(passengerRequestDto);
        }
        if(!passengerRequestDto.phone().equals(passenger.getPhone())){
            checkIfPhoneUnique(passengerRequestDto);
        }
        passengerMapper.updatePassengerFromDto(passengerRequestDto, passenger);
        passenger.setDeleted(false);
        passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    private Passenger checkIfPassengerDelete(PassengerRequest passengerRequestDto) {
        return passengerRepository
                .findByNameAndEmailAndPhoneAndDeletedIsTrue(passengerRequestDto.name(),
                                                        passengerRequestDto.email(),
                                                        passengerRequestDto.phone())
                .orElse(null);
    }

    private void checkIfPassengerUnique(PassengerRequest passengerRequestDto) {
        checkIfEmailUnique(passengerRequestDto);
        checkIfPhoneUnique(passengerRequestDto);
    }

    private void checkIfEmailUnique(PassengerRequest passengerRequestDto) {
        if(passengerRepository.existsByEmail(passengerRequestDto.email())) {
            throw new ResourceAlreadyExistsException(PASSENGER, passengerRequestDto.email());
        }
    }

    private void checkIfPhoneUnique(PassengerRequest passengerRequestDto) {
        if(passengerRepository.existsByPhone(passengerRequestDto.phone())) {
            throw new ResourceAlreadyExistsException(PASSENGER, passengerRequestDto.phone());
        }
    }

    private Passenger findPassengerById(int id) {
        return passengerRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(PASSENGER, id)
        );
    }

    private Passenger findPassengerByPhone(String phone) {
        return passengerRepository.findByPhone(phone).orElseThrow(
                ()-> new EntityNotFoundException(PASSENGER, phone)
        );
    }

    private Passenger findPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException(PASSENGER, email)
        );
    }
}

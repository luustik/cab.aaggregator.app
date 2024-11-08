package cab.aggregator.app.passengerservice.controller;

import cab.aggregator.app.passengerservice.exception.EntityNotFoundException;
import cab.aggregator.app.passengerservice.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static cab.aggregator.app.passengerservice.utils.PassengerConstants.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerControllerImpl.class)
public class PassengerControllerImplTest {

    @MockBean
    private PassengerService passengerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getPassengerById_whenPassengerExist_returnPassengerResponseAndStatusOk() throws Exception {

        when(passengerService.getPassengerById(PASSENGER_ID)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(PASSENGER_RESPONSE)));
    }

    @Test
    public void getPassengerById_whenPassengerNotExist_returnStatusNotFound() throws Exception {

        when(passengerService.getPassengerById(PASSENGER_ID)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getPassengerByPhone_whenPassengerExist_returnPassengerResponseAndStatusOk() throws Exception {

        when(passengerService.getPassengerByPhone(PASSENGER_PHONE)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_PHONE_URL, PASSENGER_PHONE);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(PASSENGER_RESPONSE)));
    }

    @Test
    public void getPassengerByPhone_whenPassengerNotExist_returnStatusNotFound() throws Exception {

        when(passengerService.getPassengerByPhone(PASSENGER_PHONE)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_PHONE_URL, PASSENGER_PHONE);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getPassengerByPhone_whenPhoneNotValid_returnStatusBadRequest() throws Exception {

        when(passengerService.getPassengerByPhone(PASSENGER_INVALID_PHONE)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_PHONE_URL, PASSENGER_INVALID_PHONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_INVALID_PHONE));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void getPassengerByEmail_whenPassengerExist_returnPassengerResponseAndStatusOk() throws Exception {

        when(passengerService.getPassengerByEmail(PASSENGER_EMAIL)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_EMAIL_URL, PASSENGER_EMAIL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(PASSENGER_RESPONSE)));
    }

    @Test
    public void getPassengerByEmail_whenPassengerNotExist_returnStatusNotFound() throws Exception {

        when(passengerService.getPassengerByEmail(PASSENGER_EMAIL)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_EMAIL_URL, PASSENGER_EMAIL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getPassengerByEmail_whenEmailNotValid_returnStatusBadRequest() throws Exception {

        when(passengerService.getPassengerByEmail(PASSENGER_INVALID_EMAIL)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_EMAIL_URL, PASSENGER_INVALID_EMAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_INVALID_EMAIL));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void getAllPassengersAdmin_returnPassengerContainerResponseAndStatusOk() throws Exception {

        when(passengerService.getAllPassengersAdmin(OFFSET, LIMIT)).thenReturn(PASSENGER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_ADMIN_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(PASSENGER_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllPassengers_returnPassengerContainerResponseAndStatusOk() throws Exception {

        when(passengerService.getAllPassengers(OFFSET, LIMIT)).thenReturn(PASSENGER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(PASSENGERS_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(PASSENGER_CONTAINER_RESPONSE)));
    }

    @Test
    public void softDeletePassengerById_whenPassengerExist_returnStatusOk() throws Exception {

        doNothing().when(passengerService).softDeletePassenger(PASSENGER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(PASSENGERS_SAFE_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void softDeletePassengerById_whenPassengerNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(passengerService).softDeletePassenger(PASSENGER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(PASSENGERS_SAFE_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void hardDeletePassengerById_whenPassengerExist_returnStatusOk() throws Exception {

        doNothing().when(passengerService).hardDeletePassenger(PASSENGER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(PASSENGERS_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void hardDeletePassengerById_whenPassengerNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(passengerService).hardDeletePassenger(PASSENGER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(PASSENGERS_ID_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void createPassenger_whenPassengerRequestValid_returnPassengerResponseAndStatusCreated() throws Exception {

        when(passengerService.createPassenger(PASSENGER_REQUEST)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(PASSENGERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(PASSENGER_RESPONSE)));
    }

    @Test
    public void createPassenger_whenPassengerRequestNotValid_returnStatusBadRequest() throws Exception {

        when(passengerService.createPassenger(PASSENGER_INVALID_REQUEST)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(PASSENGERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void updatePassenger_whenPassengerExistAndPassengerRequestValid_returnPassengerResponseAndStatusOk() throws Exception {

        when(passengerService.updatePassenger(PASSENGER_ID, PASSENGER_REQUEST)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(PASSENGERS_ID_URL, PASSENGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(PASSENGER_RESPONSE)));
    }

    @Test
    public void updatePassenger_whenPassengerNotExistAndPassengerRequestValid_returnStatusNotFound() throws Exception {

        when(passengerService.updatePassenger(PASSENGER_ID, PASSENGER_REQUEST)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(PASSENGERS_ID_URL, PASSENGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updatePassenger_whenPassengerRequestNotValid_returnStatusBadRequest() throws Exception {

        when(passengerService.updatePassenger(PASSENGER_ID, PASSENGER_INVALID_REQUEST)).thenReturn(PASSENGER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(PASSENGERS_ID_URL, PASSENGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSENGER_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }
}

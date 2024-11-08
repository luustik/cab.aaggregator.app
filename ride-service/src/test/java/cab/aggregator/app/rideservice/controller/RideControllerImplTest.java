package cab.aggregator.app.rideservice.controller;

import cab.aggregator.app.rideservice.exception.EntityNotFoundException;
import cab.aggregator.app.rideservice.service.RideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static cab.aggregator.app.rideservice.utils.RideConstants.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RideControllerImpl.class)
public class RideControllerImplTest {

    @MockBean
    private RideService rideService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getRideById_whenRideExist_returnRideResponseAndStatusOk() throws Exception {

        when(rideService.getRideById(RIDE_ID)).thenReturn(RIDE_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_ID_URL, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_RESPONSE)));
    }

    @Test
    public void getRideById_whenRideNotExist_returnStatusNotFound() throws Exception {

        when(rideService.getRideById(RIDE_ID)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_ID_URL, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getAllRides_returnRideContainerResponseAndStatusOk() throws Exception {

        when(rideService.getAllRides(OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRidesByDriverId_returnRideContainerResponseAndStatusOk() throws Exception {

        when(rideService.getAllRidesByDriverId(DRIVER_ID, OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_DRIVER_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRidesByPassengerId_returnRideContainerResponseAndStatusOk() throws Exception {

        when(rideService.getAllRidesByPassengerId(PASSENGER_ID, OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_PASSENGER_URL, PASSENGER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRidesByStatus_whenStatusValid_returnRideContainerResponseAndStatusOk() throws Exception {

        when(rideService.getAllRidesByStatus(RIDE_STATUS, OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_STATUS_URL, RIDE_STATUS);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRidesByStatus_whenStatusNotValid_returnStatusBadRequest() throws Exception {

        when(rideService.getAllRidesByStatus(RIDE_INVALID_STATUS, OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_STATUS_URL, RIDE_INVALID_STATUS);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void getAllBetweenOrderDateTime_whenDateTimeValid_returnRideContainerResponseAndStatusOk() throws Exception {

        when(rideService.getAllBetweenOrderDateTime(RIDE_START_RANGE_TIME_STR, RIDE_END_RANGE_TIME_STR, OFFSET, LIMIT))
                .thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_DATE_TIME_URL)
                .param("start", RIDE_START_RANGE_TIME_STR)
                .param("end", RIDE_END_RANGE_TIME_STR);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RIDE_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllBetweenOrderDateTime_whenDateTimeNotValid_returnStatusBadRequest() throws Exception {

        when(rideService.getAllBetweenOrderDateTime(RIDE_START_RANGE_TIME_INVALID_STR, RIDE_END_RANGE_TIME_INVALID_STR, OFFSET, LIMIT)).thenReturn(RIDE_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RIDES_DATE_TIME_URL)
                .param("start", RIDE_START_RANGE_TIME_INVALID_STR)
                .param("end", RIDE_END_RANGE_TIME_INVALID_STR);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void deleteRideById_whenRideExist_returnStatusOk() throws Exception {

        doNothing().when(rideService).deleteRide(RIDE_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(RIDES_ID_URL, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void deleteRideById_whenRideNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(rideService).deleteRide(RIDE_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(RIDES_ID_URL, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void createRide_returnRideResponseAndStatusCreated() throws Exception {

        when(rideService.createRide(RIDE_REQUEST)).thenReturn(RIDE_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(RIDES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RIDE_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(RIDE_RESPONSE)));
    }

    @Test
    public void updateRide_whenRideExist_returnRideResponseAndStatusOk() throws Exception {

        when(rideService.updateRide(RIDE_ID, RIDE_REQUEST)).thenReturn(RIDE_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(RIDES_ID_URL, RIDE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RIDE_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(RIDE_RESPONSE)));
    }

    @Test
    public void updateRide_whenRideNotExist_returnStatusNotFound() throws Exception {

        when(rideService.updateRide(RIDE_ID, RIDE_REQUEST)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(RIDES_ID_URL, RIDE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RIDE_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updateRideStatus_whenRideExistAndRideStatusValid_returnRideResponseAndStatusOk() throws Exception {

        when(rideService.updateRideStatus(RIDE_ID, RIDE_STATUS)).thenReturn(RIDE_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch(RIDES_ID_URL, RIDE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RIDE_STATUS);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(RIDE_RESPONSE)));
    }

    @Test
    public void updateRideStatus_whenRideNotExistAndRideStatusValid_returnStatusNotFound() throws Exception {

        when(rideService.updateRideStatus(RIDE_ID, RIDE_STATUS)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch(RIDES_ID_URL, RIDE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RIDE_STATUS);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updateRideStatus_whenRideStatusNotValid_returnStatusBadRequest() throws Exception {

        when(rideService.updateRideStatus(RIDE_ID, RIDE_INVALID_STATUS)).thenReturn(RIDE_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch(RIDES_ID_URL, RIDE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RIDE_INVALID_STATUS);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }
}

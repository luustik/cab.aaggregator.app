package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static cab.aggregator.app.driverservice.utils.CarConstants.CARS_BY_NUMBER_URL;
import static cab.aggregator.app.driverservice.utils.CarConstants.CARS_DRIVER_URL;
import static cab.aggregator.app.driverservice.utils.CarConstants.CARS_ID_URL;
import static cab.aggregator.app.driverservice.utils.CarConstants.CARS_URL;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_CONTAINER_RESPONSE;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_ID;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_INVALID_REQUEST;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_NUMBER;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_REQUEST;
import static cab.aggregator.app.driverservice.utils.CarConstants.CAR_RESPONSE;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_ID;
import static cab.aggregator.app.driverservice.utils.DriverConstants.LIMIT;
import static cab.aggregator.app.driverservice.utils.DriverConstants.OFFSET;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCarById_whenCarExist_returnCarResponseAndStatusOk() throws Exception {

        when(carService.getCarById(CAR_ID)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_ID_URL, CAR_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(CAR_RESPONSE)));
    }

    @Test
    public void getCarById_whenCarNotExist_returnStatusNotFound() throws Exception {

        when(carService.getCarById(CAR_ID)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_ID_URL, CAR_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getAllCars_returnCarContainerResponseAndStatusOk() throws Exception {

        when(carService.getAllCars(OFFSET, LIMIT)).thenReturn(CAR_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(CAR_CONTAINER_RESPONSE)));
    }

    @Test
    public void getCarByCarNumber_whenCarExist_returnCarResponseAndStatusOk() throws Exception {

        when(carService.getCarByCarNumber(CAR_NUMBER)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_BY_NUMBER_URL, CAR_NUMBER);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(CAR_RESPONSE)));
    }

    @Test
    public void getCarByCarNumber_whenCarNotExist_returnStatusNotFound() throws Exception {

        when(carService.getCarByCarNumber(CAR_NUMBER)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_BY_NUMBER_URL, CAR_NUMBER);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getAllCarsByDriverId_whenDriverExist_returnCarResponseAndStatusOk() throws Exception {

        when(carService.getAllCarsByDriverId(DRIVER_ID, OFFSET, LIMIT)).thenReturn(CAR_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_DRIVER_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(CAR_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllCarsByDriverId_whenDriverNotExist_returnStatusNotFound() throws Exception {

        when(carService.getAllCarsByDriverId(DRIVER_ID, OFFSET, LIMIT)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(CARS_DRIVER_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void deleteCar_whenCarExist_returnStatusOk() throws Exception {

        doNothing().when(carService).deleteCar(CAR_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(CARS_ID_URL, CAR_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void deleteCar_whenCarNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(carService).deleteCar(CAR_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(CARS_ID_URL, CAR_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void createCar_whenCarRequestValid_returnCarResponseAndStatusCreated() throws Exception {

        when(carService.createCar(CAR_REQUEST)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(CARS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CAR_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(CAR_RESPONSE)));
    }

    @Test
    public void createCar_whenCarRequestNotValid_returnStatusBadRequest() throws Exception {

        when(carService.createCar(CAR_INVALID_REQUEST)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(CARS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CAR_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void updateCar_whenCarExistAndCarRequestValid_returnCarResponseAndStatusOk() throws Exception {

        when(carService.updateCar(CAR_ID, CAR_REQUEST)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(CARS_ID_URL, CAR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CAR_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(CAR_RESPONSE)));
    }

    @Test
    public void updateCar_whenCarNotExistAndCarRequestValid_returnStatusNotFound() throws Exception {

        when(carService.updateCar(CAR_ID, CAR_REQUEST)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(CARS_ID_URL, CAR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CAR_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updateCar_whenCarRequestNotValid_returnStatusBadRequest() throws Exception {

        when(carService.updateCar(CAR_ID, CAR_INVALID_REQUEST)).thenReturn(CAR_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(CARS_ID_URL, CAR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CAR_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }
}

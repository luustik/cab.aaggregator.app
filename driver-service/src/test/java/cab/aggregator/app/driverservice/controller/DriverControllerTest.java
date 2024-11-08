package cab.aggregator.app.driverservice.controller;

import cab.aggregator.app.driverservice.exception.EntityNotFoundException;
import cab.aggregator.app.driverservice.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVERS_ADMIN_URL;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVERS_GENDER_URL;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVERS_ID_URL;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVERS_SAFE_ID_URL;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVERS_URL;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_CONTAINER_RESPONSE;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_GENDER;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_ID;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_INVALID_REQUEST;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_REQUEST;
import static cab.aggregator.app.driverservice.utils.DriverConstants.DRIVER_RESPONSE;
import static cab.aggregator.app.driverservice.utils.DriverConstants.LIMIT;
import static cab.aggregator.app.driverservice.utils.DriverConstants.OFFSET;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @MockBean
    private DriverService driverService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getDriverById_whenDriverExist_returnDriverResponseAndStatusOk() throws Exception {

        when(driverService.getDriverById(DRIVER_ID)).thenReturn(DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(DRIVERS_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(DRIVER_RESPONSE)));
    }

    @Test
    public void getDriverById_whenDriverNotExist_returnStatusNotFound() throws Exception {

        when(driverService.getDriverById(DRIVER_ID)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(DRIVERS_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getAllDriversAdmin_returnDriverContainerResponseAndStatusOk() throws Exception {

        when(driverService.getAllDriversAdmin(OFFSET, LIMIT)).thenReturn(DRIVER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(DRIVERS_ADMIN_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(DRIVER_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllDrivers_returnDriverContainerResponseAndStatusOk() throws Exception {

        when(driverService.getAllDrivers(OFFSET, LIMIT)).thenReturn(DRIVER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(DRIVERS_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(DRIVER_CONTAINER_RESPONSE)));
    }

    @Test
    public void getDriversByGender_returnDriverResponseAndStatusOk() throws Exception {

        when(driverService.getDriversByGender(DRIVER_GENDER, OFFSET, LIMIT)).thenReturn(DRIVER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(DRIVERS_GENDER_URL, DRIVER_GENDER);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(DRIVER_CONTAINER_RESPONSE)));
    }

    @Test
    public void safeDeleteDriverById_whenDriverExist_returnStatusOk() throws Exception {

        doNothing().when(driverService).safeDeleteDriver(DRIVER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(DRIVERS_SAFE_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void safeDeleteDriverById_whenDriverNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(driverService).safeDeleteDriver(DRIVER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(DRIVERS_SAFE_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void deleteDriverById_whenDriverExist_returnStatusOk() throws Exception {

        doNothing().when(driverService).deleteDriver(DRIVER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(DRIVERS_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void deleteDriverById_whenDriverNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(driverService).deleteDriver(DRIVER_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(DRIVERS_ID_URL, DRIVER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void createDriver_whenDriverRequestValid_returnDriverResponseAndStatusCreated() throws Exception {

        when(driverService.createDriver(DRIVER_REQUEST)).thenReturn(DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(DRIVERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DRIVER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(DRIVER_RESPONSE)));
    }

    @Test
    public void createDriver_whenDriverRequestNotValid_returnStatusBadRequest() throws Exception {

        when(driverService.createDriver(DRIVER_INVALID_REQUEST)).thenReturn(DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(DRIVERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DRIVER_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    public void updateDriver_wheDriverExistAndDriverRequestValid_returnDriverResponseAndStatusOk() throws Exception {

        when(driverService.updateDriver(DRIVER_ID, DRIVER_REQUEST)).thenReturn(DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(DRIVERS_ID_URL, DRIVER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DRIVER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(DRIVER_RESPONSE)));
    }

    @Test
    public void updateDriver_whenDriverNotExistAndDriverRequestValid_returnStatusNotFound() throws Exception {

        when(driverService.updateDriver(DRIVER_ID, DRIVER_REQUEST)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(DRIVERS_ID_URL, DRIVER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DRIVER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void updateDriver_whenDriverRequestNotValid_returnStatusBadRequest() throws Exception {

        when(driverService.updateDriver(DRIVER_ID, DRIVER_INVALID_REQUEST)).thenReturn(DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(DRIVERS_ID_URL, DRIVER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DRIVER_INVALID_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isBadRequest());
    }
}

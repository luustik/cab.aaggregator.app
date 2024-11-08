package cab.aggregator.app.ratingservice.controller;

import cab.aggregator.app.ratingservice.controller.impl.RatingController;
import cab.aggregator.app.ratingservice.exception.EntityNotFoundException;
import cab.aggregator.app.ratingservice.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static cab.aggregator.app.ratingservice.utils.RatingConstants.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @MockBean
    private RatingService ratingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getRatingById_whenRatingExist_returnRatingResponseAndStatusOk() throws Exception {

        when(ratingService.getRatingById(RATING_ID)).thenReturn(RATING_DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_ID_URL, RATING_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RATING_DRIVER_RESPONSE)));
    }

    @Test
    public void getRatingById_whenRatingNotExist_returnStatusNotFound() throws Exception {

        when(ratingService.getRatingById(RATING_ID)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_ID_URL, RATING_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getRatingByRideIdAndRole_whenRatingExist_returnRatingResponseAndStatusOk() throws Exception {

        when(ratingService.getRatingByRideIdAndRole(RIDE_ID, DRIVER_ROLE)).thenReturn(RATING_DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_RIDE_ROLE_URL, DRIVER_ROLE, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RATING_DRIVER_RESPONSE)));
    }

    @Test
    public void getRatingByRideIdAndRole_whenRatingNotExist_returnStatusNotFound() throws Exception {

        when(ratingService.getRatingByRideIdAndRole(RIDE_ID, DRIVER_ROLE)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_RIDE_ROLE_URL, DRIVER_ROLE, RIDE_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void getAllRatings_returnRatingContainerResponseAndStatusOk() throws Exception {

        when(ratingService.getAllRatings(OFFSET, LIMIT)).thenReturn(RATING_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_URL);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RATING_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRatingsByRole_returnRatingContainerResponseAndStatusOk() throws Exception {

        when(ratingService.getAllByRole(DRIVER_ROLE, OFFSET, LIMIT)).thenReturn(RATING_DRIVER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_ROLE_URL, DRIVER_ROLE);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RATING_DRIVER_CONTAINER_RESPONSE)));
    }

    @Test
    public void getAllRatingsByUserIdAndRole_returnRatingContainerResponseAndStatusOk() throws Exception {

        when(ratingService.getAllByUserIdAndRole(USER_ID, DRIVER_ROLE, OFFSET, LIMIT)).thenReturn(RATING_DRIVER_CONTAINER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_USER_URL, DRIVER_ROLE, USER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(RATING_DRIVER_CONTAINER_RESPONSE)));
    }

    @Test
    public void deleteRatingById_whenRatingExist_returnStatusOk() throws Exception {

        doNothing().when(ratingService).deleteRating(RATING_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(RATINGS_ID_URL, RATING_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNoContent());
    }

    @Test
    public void deleteRatingById_whenRatingNotExist_returnStatusNotfound() throws Exception {

        doThrow(EntityNotFoundException.class).when(ratingService).deleteRating(RATING_ID);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = delete(RATINGS_ID_URL, RATING_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void createRating_returnRatingResponseAndStatusCreated() throws Exception {

        when(ratingService.createRating(RATING_DRIVER_REQUEST)).thenReturn(RATING_DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(RATINGS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RATING_DRIVER_REQUEST));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(RATING_DRIVER_RESPONSE)));
    }

    @Test
    public void updateRating_whenRatingExist_returnRatingResponseAndStatusOk() throws Exception {

        when(ratingService.updateRating(RATING_ID, RATING_UPDATE_DTO)).thenReturn(RATING_DRIVER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch(RATINGS_ID_URL, RATING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RATING_UPDATE_DTO));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(RATING_DRIVER_RESPONSE)));
    }

    @Test
    public void updateRating_whenRatingNotExist_returnStatusNotFound() throws Exception {

        when(ratingService.updateRating(RATING_ID, RATING_UPDATE_DTO)).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = patch(RATINGS_ID_URL, RATING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RATING_UPDATE_DTO));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    public void calculateAvgRatingUser_returnAvgRatingResponseAndStatusOk() throws Exception {
        when(ratingService.calculateRating(USER_ID, DRIVER_ROLE)).thenReturn(AVG_RATING_USER_RESPONSE);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(RATINGS_AVG_RATING_URL, DRIVER_ROLE, USER_ID);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(AVG_RATING_USER_RESPONSE)));
    }

}

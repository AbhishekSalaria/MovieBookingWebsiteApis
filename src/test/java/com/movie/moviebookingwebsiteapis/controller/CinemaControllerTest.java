package com.movie.moviebookingwebsiteapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.MovieShowtime;
import com.movie.moviebookingwebsiteapis.service.CinemaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters=false)
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class CinemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CinemaService service;

    @Test
    public void testaddNewCinema() throws Exception{
        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        when(service.addNewCinema(cinema)).thenReturn(cinema);

        String inputJson = this.convertToJson(cinema);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/cinema/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        assertEquals(mvcResult.getResponse().getContentAsString(),inputJson);

    }

    @Test
    public void testgetAllCinemas() throws Exception{
        List<Cinema> cinemas = Arrays.asList(
                new Cinema(1L,"dummyCinema","dummyCity")
        );

        when(service.getAllCinemas()).thenReturn(cinemas);

        RequestBuilder request = MockMvcRequestBuilders.get("/cinema/cinemas");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        assertEquals(mvcResult.getResponse().getContentAsString(),this.convertToJson(cinemas));
    }

    @Test
    public void testgetCinemaById() throws Exception{
        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        when(service.getCinemaById(1L)).thenReturn(cinema);

        RequestBuilder request = MockMvcRequestBuilders.get("/cinema/cinema/1");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        assertEquals(mvcResult.getResponse().getContentAsString(),this.convertToJson(cinema));
    }

    @Test
    public void testgetCinemasPlayingMovie() throws Exception {
        List<MovieShowtime> movieShowtimes = Arrays.asList(
                new MovieShowtime("dummyCinema","dummyCity", LocalDateTime.now())
        );

        when(service.getCinemasPlayingMovie("dummy")).thenReturn(movieShowtimes);

        RequestBuilder request = MockMvcRequestBuilders.get("/cinema/cinema/movie/dummy");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
package com.movie.moviebookingwebsiteapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieTitle;
import com.movie.moviebookingwebsiteapis.service.*;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MovieService service;

    @Test
    public void testaddNewMovie() throws Exception{
        Movie movie = new Movie(1L,"dummy");

        when(service.addNewMovie(movie)).thenReturn(movie);

        String inputJson = this.convertToJson(movie);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/movie/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void testgetAllMovies() throws  Exception{
        List<Movie> movies = Arrays.asList(
                new Movie(1L,"dummy")
        );

        when(service.getAllMovies()).thenReturn(movies);

        RequestBuilder request = MockMvcRequestBuilders.get("/movie/movies");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void testgetMovieById() throws Exception{
        Movie movie = new Movie(1L,"dummy");

        when(service.getMovieById(1L)).thenReturn(movie);

        RequestBuilder request = MockMvcRequestBuilders.get("/movie/movie/1");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);

    }

    @Test
    void testgetMovieByCity() throws Exception{
        List<MovieTitle> movies = Arrays.asList(new MovieTitle("dummy"));

        when(service.getMovieByCity(anyString())).thenReturn(movies);

        RequestBuilder request = MockMvcRequestBuilders.get("/movie/movie/city/dummy");

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
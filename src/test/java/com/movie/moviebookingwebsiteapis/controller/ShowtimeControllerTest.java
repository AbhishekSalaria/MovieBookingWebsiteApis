package com.movie.moviebookingwebsiteapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.service.ShowtimeService;
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
class ShowtimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShowtimeService service;

    @Test
    public void testaddNewShowtime() throws Exception{

        Showtime showtime = new Showtime(1L,1L,1L, LocalDateTime.now(),100);

        when(service.addNewShowtime(showtime)).thenReturn(showtime);

        String inputJson = this.convertToJson(showtime);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/showtime/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);

        String result = mvcResult.getResponse().getContentAsString();
        long id = responseJson(result).get("id").asLong();
        assertEquals(id,showtime.getId());

    }

    @Test
    public void testgetAllShowtimes() throws Exception{
        List<Showtime> showtimes = Arrays.asList(
                new Showtime(1L,1L,1L, LocalDateTime.now(),100)
        );
        when(service.getAllShowtimes()).thenReturn(showtimes);

        RequestBuilder request = MockMvcRequestBuilders.get("/showtime/showtimes");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        int size = responseJson(result).size();

        assertEquals(200,status);
        assertEquals(size,showtimes.size());
    }

    @Test
    public void testgetShowtimeById() throws Exception{
        Showtime showtime = new Showtime(1L,1L,1L, LocalDateTime.now(),100);

        when(service.getShowtimeById(1L)).thenReturn(showtime);

        RequestBuilder request = MockMvcRequestBuilders.get("/showtime/showtime/1");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        long id = responseJson(result).get("id").asLong();

        assertEquals(200,status);
        assertEquals(id,showtime.getId());
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    private JsonNode responseJson(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content);
    }
}
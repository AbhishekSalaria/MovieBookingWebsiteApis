package com.movie.moviebookingwebsiteapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.moviebookingwebsiteapis.entity.Booking;
import com.movie.moviebookingwebsiteapis.service.BookingService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters=false)
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookingService service;

    @Test
    public void testgetAllBookings() throws Exception{
        List<Booking> bookings = Arrays.asList(
                new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100)
        );
        when(service.getAllBookings()).thenReturn(bookings);

        RequestBuilder request = MockMvcRequestBuilders.get("/booking/all");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);

        long returnedId = this.responseJson(mvcResult.getResponse().getContentAsString()).get(0).get("id").asLong();
        assertEquals(returnedId,bookings.get(0).getId());
    }

    @Test
    public void testgetByBookingNumber() throws Exception{
        Booking booking = new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100);
        when(service.getByBookingNumber(anyString())).thenReturn(booking);

        RequestBuilder request = MockMvcRequestBuilders.get("/booking/bookingNumber/bookingNumber");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        long id = this.responseJson(result).get("id").asLong();

        assertEquals(200,status);
        assertEquals(id,booking.getId());
    }

    @Test
    public void testbookMovieTickets() throws Exception{
        Booking booking = new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),10);
        when(service.bookMovieTickets(1L,10)).thenReturn(booking);

        String inputJson = this.convertToJson(booking);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/booking/book/1/10")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        long id = this.responseJson(result).get("id").asLong();

        assertEquals(200,status);
        assertEquals(id,booking.getId());
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
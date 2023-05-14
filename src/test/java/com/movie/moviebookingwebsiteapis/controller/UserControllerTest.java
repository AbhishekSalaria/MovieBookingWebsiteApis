package com.movie.moviebookingwebsiteapis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.movie.moviebookingwebsiteapis.entity.User;
import com.movie.moviebookingwebsiteapis.service.UserService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters=false)
@SpringBootTest
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService service;

    @Test
    public void testaddNewUser() throws Exception{
        User user = new User(1L,"dummy","dummy");
        when(service.addNewUser(user)).thenReturn(user);

        String inputJson = this.convertToJson(user);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        assertEquals(mvcResult.getResponse().getContentAsString(),inputJson);

    }

    @Test
    public void testgetAllUsers() throws Exception{
        List<User> users = Arrays.asList(
                new User(1L,"dummy","dummy")
        );
        when(service.getAllUsers()).thenReturn(users);

        RequestBuilder request = MockMvcRequestBuilders.get("/user/users");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        int size = responseJson(result).size();

        assertEquals(200,status);
        assertEquals(size,users.size());
    }

    @Test
    public void testgetUserById() throws Exception{
        User user = new User(1L,"dummy","dummy");
        when(service.getUserById(1L)).thenReturn(user);

        RequestBuilder request = MockMvcRequestBuilders.get("/user/user/1");

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        String result = mvcResult.getResponse().getContentAsString();
        long id = responseJson(result).get("id").asLong();

        assertEquals(200,status);
        assertEquals(id,user.getId());
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
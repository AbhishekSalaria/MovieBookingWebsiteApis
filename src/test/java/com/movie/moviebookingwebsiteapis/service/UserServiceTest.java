package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.User;
import com.movie.moviebookingwebsiteapis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repository;

    @Test
    void testaddNewUser() {
        User user = new User(1L,"dummy","dummy");

        when(repository.save(user)).thenReturn(user);

        User returned = service.addNewUser(user);

        assertEquals(returned.getId(),user.getId());
    }

    @Test
    void testgetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1L,"dummy","dummy")
        );

        when(repository.findAll()).thenReturn(users);

        List<User> returned = service.getAllUsers();

        assertEquals(returned.size(),users.size());
    }

    @Test
    void testgetUserById() throws Exception{
        User user = new User(1L,"dummy","dummy");

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User returned = service.getUserById(1L);

        assertEquals(returned.getId(),user.getId());
    }
}
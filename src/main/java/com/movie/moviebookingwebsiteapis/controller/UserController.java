package com.movie.moviebookingwebsiteapis.controller;

import com.movie.moviebookingwebsiteapis.entity.User;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/add")
    public User addNewUser(@RequestBody User user) {
        return service.addNewUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) throws NoElementFoundException {
        return service.getUserById(id);
    }
}

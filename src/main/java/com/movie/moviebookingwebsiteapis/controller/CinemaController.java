package com.movie.moviebookingwebsiteapis.controller;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.MovieShowtime;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    CinemaService service;

    @PostMapping("/add")
    public Cinema addNewCinema(@RequestBody Cinema cinema) {
        return service.addNewCinema(cinema);
    }

    @GetMapping("/cinemas")
    public List<Cinema> getAllCinemas() {
        return service.getAllCinemas();
    }

    @GetMapping("/cinema/{id}")
    public Cinema getCinemaById(@PathVariable long id) throws NoElementFoundException {
        return service.getCinemaById(id);
    }

    @GetMapping("/cinema/movie/{movie}")
    public List<MovieShowtime> getCinemasPlayingMovie(@PathVariable String movie) throws NoElementFoundException {
        return service.getCinemasPlayingMovie(movie);
    }
}

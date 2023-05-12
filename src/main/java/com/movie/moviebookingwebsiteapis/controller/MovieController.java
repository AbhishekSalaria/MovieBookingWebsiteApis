package com.movie.moviebookingwebsiteapis.controller;

import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieTitle;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService service;

    @PostMapping("/add")
    public Movie addNewMovie(@RequestBody Movie movie) {
        return service.addNewMovie(movie);
    }

    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable long id) throws NoElementFoundException {
        return service.getMovieById(id);
    }

    @GetMapping("/movie/city/{city}")
    public List<MovieTitle> getMovieByCity(@PathVariable String city) throws NoElementFoundException {
        return service.getMovieByCity(city);
    }

}

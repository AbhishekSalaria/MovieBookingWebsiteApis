package com.movie.moviebookingwebsiteapis.controller;

import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtime")
public class ShowtimeController {

    @Autowired
    ShowtimeService service;

    @PostMapping("/add")
    public Showtime addNewShowtime(@RequestBody Showtime showtime) throws NoElementFoundException {
        return service.addNewShowtime(showtime);
    }

    @GetMapping("/showtimes")
    public List<Showtime> getAllShowtimes() {
        return service.getAllShowtimes();
    }

    @GetMapping("/showtime/{id}")
    public  Showtime getShowtimeById(@PathVariable long id) throws NoElementFoundException {
        return service.getShowtimeById(id);
    }

}

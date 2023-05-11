package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.repository.CinemaRepository;
import com.movie.moviebookingwebsiteapis.repository.MovieRepository;
import com.movie.moviebookingwebsiteapis.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {

    @Autowired
    ShowtimeRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    public Showtime addNewShowtime(Showtime showtime) throws NoElementFoundException {

        Movie movieIdFound = movieRepository.findById(showtime.getMovie_id()).orElse(null);
        if(movieIdFound == null){
            throw new NoElementFoundException(String.format("No movie with the given movie id found: %d.", showtime.getMovie_id()));
        }
        Cinema cinemaIdFound = cinemaRepository.findById(showtime.getCinema_id()).orElse(null);
        if(cinemaIdFound == null) {
            throw new NoElementFoundException(String.format("No cinema with the given cinema id found: %d.", showtime.getCinema_id()));
        }
        return repository.save(showtime);
    }

    public List<Showtime> getAllShowtimes() {
        return repository.findAll();
    }

    public Showtime getShowtimeById(long id) throws NoElementFoundException {

        Showtime showtime = repository.findById(id).orElse(null);

        if(showtime == null) {
            throw new NoElementFoundException(String.format("No showtime with the given showtime id found: %d.", id));
        }

        return showtime;
    }
}

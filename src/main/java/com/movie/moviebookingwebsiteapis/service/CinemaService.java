package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieShowtime;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.repository.CinemaRepository;
import com.movie.moviebookingwebsiteapis.repository.MovieRepository;
import com.movie.moviebookingwebsiteapis.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CinemaService {

    @Autowired
    CinemaRepository repository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    MovieRepository movieRepository;

    public Cinema addNewCinema(Cinema cinema) {
        return repository.save(cinema);
    }

    public List<Cinema> getAllCinemas() {
        return repository.findAll();
    }

    public Cinema getCinemaById(long id) throws NoElementFoundException {

        Cinema cinema = repository.findById(id).orElse(null);

        if(cinema == null) {
            throw new NoElementFoundException(String.format("No Cinema with the given id found: %d.", id));
        }
        return cinema;
    }

    public List<MovieShowtime> getCinemasPlayingMovie(String title) throws NoElementFoundException {
        List<MovieShowtime> movieShowtimes = new ArrayList<MovieShowtime>();
        Movie movie = movieRepository.findMovieByTitle(title);

        if(movie == null) {
            throw new NoElementFoundException(String.format("No Movie with the given title found: %s.", title));
        }

        List<Showtime> showtimes = showtimeRepository.findAll().stream()
                .filter(x -> x.getMovie_id() == movie.getId())
                .toList();

        if(showtimes.size() == 0) {
            throw new NoElementFoundException(String.format("No Showtimes for the movie found: %s.", title));
        }

        for(Showtime showtime: showtimes) {
            long cinema_id = showtime.getCinema_id();
            Cinema cinema = repository.findById(cinema_id).orElse(null);
            MovieShowtime movieShowtime = new MovieShowtime();
            movieShowtime.setCinema(cinema.getName());
            movieShowtime.setLocation(cinema.getCity());
            movieShowtime.setStartTime(showtime.getStartTime());
            movieShowtimes.add(movieShowtime);
            }
        return movieShowtimes;
        }
    }

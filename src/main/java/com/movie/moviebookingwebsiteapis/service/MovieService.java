package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieTitle;
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
public class MovieService {

    @Autowired
    MovieRepository repository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    public Movie addNewMovie(Movie movie) {
        return repository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie getMovieById(long id) throws NoElementFoundException {

        Movie movie = repository.findById(id).orElse(null);

        if(movie == null) {
            throw new NoElementFoundException(String.format("No Movie with movie id found: %d.", id));
        }
        return movie;
    }

    public List<MovieTitle> getMovieByCity(String city) throws NoElementFoundException {

        List<MovieTitle> movies = new ArrayList<MovieTitle>();
        Set<Long> movie_ids = new HashSet<Long>();
        List<Cinema> cinemas = cinemaRepository.findCinemaByCity(city);

        if(cinemas.size() == 0) {
            throw new NoElementFoundException("No Movies in the Given City Found.");
        }

        for(Cinema cinema: cinemas) {
            long id = cinema.getId();
            List<Showtime> showtimes = showtimeRepository.findAll().stream()
                                                        .filter(x -> x.getCinema_id() == id)
                                                        .toList();
            for(Showtime showtime: showtimes) {
                long movie_id = showtime.getMovie_id();
                if(movie_ids.contains(movie_id)) {
                    continue;
                }
                else {
                    movie_ids.add(movie_id);
                    Movie movie = repository.findById(movie_id).orElse(null);
                    MovieTitle movieTitle = new MovieTitle();
                    movieTitle.setTitle(movie.getTitle());
                    movies.add(movieTitle);
                }
            }
        }

        if(movies.size() == 0) {
            throw  new NoElementFoundException("No Movies in the Given City Found.");
        }
        return  movies;
    }
}

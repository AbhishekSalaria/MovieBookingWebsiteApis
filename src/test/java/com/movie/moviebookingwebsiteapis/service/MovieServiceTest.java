package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieTitle;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.repository.CinemaRepository;
import com.movie.moviebookingwebsiteapis.repository.MovieRepository;
import com.movie.moviebookingwebsiteapis.repository.ShowtimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieService movieService;

    @Mock
    MovieRepository repository;

    @Mock
    ShowtimeRepository showtimeRepository;

    @Mock
    CinemaRepository cinemaRepository;

    @Test
    public void testaddNewMovie() {
        Movie movie = new Movie(1L,"dummy");

        when(repository.save(movie)).thenReturn(movie);

        Movie returned = movieService.addNewMovie(movie);

        assertEquals(returned.getId(),movie.getId());
    }

    @Test
    public void testgetAllMovies() {
        List<Movie> movies = Arrays.asList(
                new Movie(1L,"dummy1"),
                new Movie(2L,"dummy2")
        );

        when(repository.findAll()).thenReturn(movies);

        List<Movie> returned = movieService.getAllMovies();

        assertEquals(returned.size(),2);
    }

    @Test
    public void testgetMovieById() throws Exception {
        Movie movie = new Movie(1L,"dummy");

        when(repository.findById(1L)).thenReturn(Optional.of(movie));

        Movie returned = movieService.getMovieById(1L);

        assertEquals(returned.getId(),movie.getId());
    }

    @Test
    public void testgetMovieByCity() throws Exception{
        List<MovieTitle> movieTitles = Arrays.asList(
                new MovieTitle("dummy")
        );

        List<Cinema> cinemas = Arrays.asList(
                new Cinema(1L,"dummyCinema","dummyCity")
        );

        List<Showtime> showtimes = Arrays.asList(
                new Showtime(1L,1L,1L,LocalDateTime.now(),100)
        );

        when(cinemaRepository.findCinemaByCity("dummyCity")).thenReturn(cinemas);
        when(showtimeRepository.findAll()).thenReturn(showtimes);
        when(repository.findById(1L)).thenReturn(Optional.of(new Movie(1L, "dummy")));

        List<MovieTitle> returned = movieService.getMovieByCity("dummyCity");

        assertEquals(returned.size(),movieTitles.size());
    }
}
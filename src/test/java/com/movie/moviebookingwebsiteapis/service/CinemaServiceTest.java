package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
import com.movie.moviebookingwebsiteapis.entity.MovieShowtime;
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
class CinemaServiceTest {

    @InjectMocks
    CinemaService service;

    @Mock
    CinemaRepository repository;

    @Mock
    ShowtimeRepository showtimeRepository;

    @Mock
    MovieRepository movieRepository;

    @Test
    public void testaddNewCinema() {
        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        when(repository.save(cinema)).thenReturn(cinema);

        Cinema returned = service.addNewCinema(cinema);

        assertEquals(returned.getId(),cinema.getId());
    }

    @Test
    public void testgetAllCinemas(){
        List<Cinema> cinemas = Arrays.asList(
                new Cinema(1L,"dummyCinema","dummyCity")
        );

        when(repository.findAll()).thenReturn(cinemas);

        List<Cinema> returned = service.getAllCinemas();

        assertEquals(returned.size(),cinemas.size());
    }

    @Test
    public void testgetCinemaById() throws Exception{
        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        when(repository.findById(1L)).thenReturn(Optional.of(cinema));

        Cinema returned = service.getCinemaById(1L);

        assertEquals(returned.getId(),cinema.getId());
    }

    @Test
    public void testgetCinemasPlayingMovie() throws Exception{
        List<MovieShowtime> movieShowtimes = Arrays.asList(
                new MovieShowtime("dummyCinema","dummyCity", LocalDateTime.now())
        );

        Movie movie = new Movie(1L,"dummy");

        List<Showtime> showtimes = Arrays.asList(
                new Showtime(1L,1L,1L,LocalDateTime.now(),100)
        );

        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        when(movieRepository.findMovieByTitle("dummy")).thenReturn(movie);
        when(showtimeRepository.findAll()).thenReturn(showtimes);
        when(repository.findById(1L)).thenReturn(Optional.of(cinema));

        List<MovieShowtime> returned = service.getCinemasPlayingMovie("dummy");

        assertEquals(returned.get(0).getCinema(),movieShowtimes.get(0).getCinema());
    }
}
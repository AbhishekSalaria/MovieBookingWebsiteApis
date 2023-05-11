package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import com.movie.moviebookingwebsiteapis.entity.Movie;
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
class ShowtimeServiceTest {

    @InjectMocks
    ShowtimeService service;

    @Mock
    ShowtimeRepository repository;

    @Mock
    MovieRepository movieRepository;

    @Mock
    CinemaRepository cinemaRepository;

    @Test
    public void testaddNewShowtime() throws Exception{
        Showtime showtime = new Showtime(1L,1L,1L, LocalDateTime.now(),100);

        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        Movie movie = new Movie(1L,"dummy");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));

        when(repository.save(showtime)).thenReturn(showtime);

        Showtime returned = service.addNewShowtime(showtime);

        assertEquals(returned.getSeatAvailability(),showtime.getSeatAvailability());
    }

    @Test
    public void testgetAllShowtimes() {
        List<Showtime> showtimes = Arrays.asList(
                new Showtime(1L,1L,1L, LocalDateTime.now(),100)
        );

        when(repository.findAll()).thenReturn(showtimes);

        List<Showtime> returned = service.getAllShowtimes();

        assertEquals(returned.size(),showtimes.size());
    }

    @Test
    public void testgetShowtimeById() throws Exception{
        Showtime showtime = new Showtime(1L,1L,1L, LocalDateTime.now(),100);

        when(repository.findById(1L)).thenReturn(Optional.of(showtime));

        Showtime returned = service.getShowtimeById(1L);

        assertEquals(returned.getId(),showtime.getId());
    }
}
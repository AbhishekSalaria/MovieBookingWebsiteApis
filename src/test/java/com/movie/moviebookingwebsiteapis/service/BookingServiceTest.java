package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Booking;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.repository.BookingRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    BookingService service;

    @Mock
    BookingRepository repository;

    @Mock
    ShowtimeRepository showtimeRepository;

    @Test
    public void testgetAllBookings() {
        List<Booking> bookings = Arrays.asList(
                new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100)
        );

        when(repository.findAll()).thenReturn(bookings);

        List<Booking> returned = service.getAllBookings();

        assertEquals(returned.size(),bookings.size());
    }

    @Test
    public void testgetByBookingNumber() throws Exception{
        Booking booking = new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100);

        when(repository.findByBookingNumber(anyString())).thenReturn(booking);

        Booking returned = service.getByBookingNumber("bookingNumber");

        assertEquals(returned.getId(),booking.getId());
    }

    @Test
    public void testbookMovieTickets() throws  Exception{
        Booking booking = new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100);

        Showtime showtime = new Showtime(1L,1L,1L,LocalDateTime.now(),100);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

        when(repository.save(any(Booking.class))).thenReturn(booking);

        when(showtimeRepository.save(showtime)).thenReturn(showtime);

        Booking returned = service.bookMovieTickets(1L,10);

        assertEquals(returned.getMovie_id(),booking.getMovie_id());

    }

}
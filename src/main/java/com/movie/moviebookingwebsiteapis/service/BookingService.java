package com.movie.moviebookingwebsiteapis.service;

import com.movie.moviebookingwebsiteapis.entity.Booking;
import com.movie.moviebookingwebsiteapis.entity.Showtime;
import com.movie.moviebookingwebsiteapis.exception.InvalidValueException;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.repository.BookingRepository;
import com.movie.moviebookingwebsiteapis.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository repository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public Booking getByBookingNumber(String bookingNumber) throws NoElementFoundException {

        Booking booking = repository.findByBookingNumber(bookingNumber);

        if (booking == null) {
            throw new NoElementFoundException(String.format("No Booking with the given booking number: %s found.", bookingNumber));
        }

        return booking;
    }

    public Booking bookMovieTickets(long showTimeId, int numberOfTickets) throws InvalidValueException, NoElementFoundException {
        Showtime showtime = showtimeRepository.findById(showTimeId).orElse(null);

        if(showtime == null) {
            throw new NoElementFoundException(String.format("No Showtime with the given showtime id found: %d.", showTimeId));
        }

        if(numberOfTickets <= 0) {
            throw new InvalidValueException("Number of tickets to be booked should be >= 1.");
        }

        if(numberOfTickets > showtime.getSeatAvailability()) {
            throw new InvalidValueException(String.format("Can Book only <= %d tickets.", showtime.getSeatAvailability()));
        }

        Booking booking = new Booking();
        booking.setMovie_id(showtime.getMovie_id());
        booking.setCinema_id(showtime.getCinema_id());
        booking.setStartTime(showtime.getStartTime());
        booking.setNumberOfTicketsBooked(numberOfTickets);

        showtime.setSeatAvailability(showtime.getSeatAvailability() - numberOfTickets);
        showtimeRepository.save(showtime);

        return repository.save(booking);
    }
}
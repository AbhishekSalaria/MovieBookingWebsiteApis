package com.movie.moviebookingwebsiteapis.controller;

import com.movie.moviebookingwebsiteapis.entity.Booking;
import com.movie.moviebookingwebsiteapis.exception.InvalidValueException;
import com.movie.moviebookingwebsiteapis.exception.NoElementFoundException;
import com.movie.moviebookingwebsiteapis.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService service;

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/bookingNumber/{bookingNumber}")
    public Booking getByBookingNumber(@PathVariable String bookingNumber) throws NoElementFoundException {
        return service.getByBookingNumber(bookingNumber);
    }

    @PostMapping("/book/{showTimeId}/{numberOfTickets}")
    public Booking bookMovieTickets(@PathVariable long showTimeId, @PathVariable int numberOfTickets) throws InvalidValueException, NoElementFoundException {
        return service.bookMovieTickets(showTimeId,numberOfTickets);
    }
}

package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookingRepository repository;

    @Test
    public void testfindByBookingNumber() {
        Booking booking = new Booking(1L,"bookingNumber",1L,1L, LocalDateTime.now(),100);
        Booking managedBooking = entityManager.merge(booking);
        entityManager.persist(managedBooking);

        Booking returned = repository.findByBookingNumber("bookingNumber");
        assertEquals("bookingNumber",returned.getBookingNumber());
    }
}
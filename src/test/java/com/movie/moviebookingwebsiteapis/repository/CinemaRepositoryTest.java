package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CinemaRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CinemaRepository repository;

    @Test
    public void testfindCinemaByCity() {

        Cinema cinema = new Cinema(1L,"dummyCinema","dummyCity");

        List<Cinema> cinemas = Arrays.asList(
                new Cinema(1L,"dummyCinema","dummyCity")
        );
        Cinema managedCinemas = entityManager.merge(cinema);
        entityManager.persist(managedCinemas);

        List<Cinema> returned = repository.findCinemaByCity("dummyCity");
        assertEquals(1L,returned.get(0).getId());
    }
}
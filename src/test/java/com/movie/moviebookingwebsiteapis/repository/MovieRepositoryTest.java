package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MovieRepository repository;

    @Test
    public void findMovieByTitle() {
        Movie movie = new Movie(1L,"dummy");
        Movie managedMovie = entityManager.merge(movie);
        entityManager.persist(managedMovie);

        Movie returned = repository.findMovieByTitle("dummy");
        assertEquals(returned.getTitle(),movie.getTitle());
    }
}
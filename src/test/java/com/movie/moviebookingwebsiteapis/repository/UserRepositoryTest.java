package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    public void findByUsername() {
        User user = new User(1L,"dummyName","dummyPassword");
        User managedUser = entityManager.merge(user);
        entityManager.persist(managedUser);

        User returned = repository.findByUsername("dummyName").orElse(null);

        assertEquals(returned.getUsername(),user.getUsername());
    }
}
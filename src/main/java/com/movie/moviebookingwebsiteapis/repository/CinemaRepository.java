package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
    List<Cinema> findCinemaByCity(String city);
}

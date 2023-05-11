package com.movie.moviebookingwebsiteapis.repository;

import com.movie.moviebookingwebsiteapis.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
}

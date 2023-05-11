package com.movie.moviebookingwebsiteapis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="STM_TBL")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long movie_id;

    @Column(nullable = false)
    private long cinema_id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private int seatAvailability;
}

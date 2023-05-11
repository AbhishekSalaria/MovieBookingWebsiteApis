package com.movie.moviebookingwebsiteapis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BKG_TBL")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String bookingNumber = UUID.randomUUID().toString().toUpperCase();

    @Column(nullable = false)
    private long movie_id;

    @Column(nullable = false)
    private long cinema_id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private int numberOfTicketsBooked;
}

package com.movie.moviebookingwebsiteapis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieShowtime {
    private String cinema;
    private String location;
    private LocalDateTime startTime;
}

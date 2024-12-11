package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;

import java.time.LocalDateTime;

public record ScreeningDto(Movie movie, Room room, LocalDateTime startTime) {
}

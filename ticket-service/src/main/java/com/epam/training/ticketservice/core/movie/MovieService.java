package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<UserDto> describe();

    void createMovie(String title, String genre, int length);

    void updateMovie(String title, String genre, int length);

    void deleteMovie(String title);

    List<MovieDto> listMovies();

}

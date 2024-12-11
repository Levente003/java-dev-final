package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    private final MovieService movieService;
    private final UserService userService;


    @ShellMethod(value = "Create a new movie. Usage: create-movie <title> <genre> <length>", key = "create movie")
    public String createMovie(String title, String genre, int length) {
        try {
            movieService.createMovie(title, genre, length);
            return "Movie created successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update an existing movie. Usage: update-movie <title> <genre> <length>", key = "update movie")
    public String updateMovie(String title, String genre, int length) {
        try {
            movieService.updateMovie(title, genre, length);
            return "Movie updated successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Delete a movie. Usage: delete-movie <title>", key = "delete movie")
    public String deleteMovie(String title) {
        try {
            movieService.deleteMovie(title);
            return "Movie deleted successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "List all movies. Usage: list-movies", key = "list movies")
    public String listMovies() {
        List<MovieDto> movies = movieService.listMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        StringBuilder sb = new StringBuilder();
        movies.forEach(movie -> sb.append(movie.toString()).append("\n"));
        return sb.toString().trim();
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }

}

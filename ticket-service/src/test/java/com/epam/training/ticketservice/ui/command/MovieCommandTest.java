package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.ui.commands.MovieCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieCommandTest {
    public static final String MOVIE_TITLE = "Test Movie";
    public static final String MOVIE_CATEGORY = "animation";
    public static final int MOVIE_LENGTH = 90;
    MovieDto MOVIE_DTO = new MovieDto(MOVIE_TITLE, MOVIE_CATEGORY, MOVIE_LENGTH);

    private final MovieService movieService = mock(MovieServiceImpl.class);
    private final UserService userService = mock(UserServiceImpl.class);
    private final MovieCommand underTest = new MovieCommand(movieService, userService);
    @Test
    void listMoviesWhenThereAreNoMovies() {
        when(movieService.listMovies()).thenReturn(List.of());

        String result = underTest.listMovies();

        assertEquals("There are no movies at the moment", result);
        verify(movieService).listMovies();
    }
    @Test
    void listMoviesWhenThereAreMovies() {
        when(movieService.listMovies()).thenReturn(List.of(MOVIE_DTO));

        String result = underTest.listMovies();

        assertEquals("Test Movie (animation, 90 minutes)", result);
        verify(movieService).listMovies();
    }
}

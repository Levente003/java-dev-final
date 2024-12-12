package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {
    private static final Movie ENTITY = new Movie("Test Movie", "animation", 90);

    private final MovieRepository movieRepo = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepo);

    @Test
    void testCreateMovieShouldStoreTheGivenMovieWhenTheInputMovieIsValid() {
        // Given
        when(movieRepo.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.createMovie("Test Movie", "animation", 90);

        // Then
        verify(movieRepo).save(ENTITY);
    }

    @Test
    void testDeleteMovieShouldDeleteTheGivenMovieWhenTheInputTitleIsValid() {
        // Given
        when(movieRepo.findByTitle("Test Movie")).thenReturn(Optional.of(ENTITY));

        // When
        underTest.deleteMovie("Test Movie");

        // Then
        verify(movieRepo).delete(ENTITY);
    }
    @Test
    void testDeleteMovieShouldThrowIllegalArgumentExceptionWhenTheInputTitleIsInvalid() {
        // Given
        when(movieRepo.findByTitle("Movie Title")).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteMovie("Movie Title"));

    }
}

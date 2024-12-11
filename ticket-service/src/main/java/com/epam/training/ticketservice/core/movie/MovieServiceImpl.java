package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.user.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepo;

    @Override
    public Optional<UserDto> describe() {
        return Optional.empty();
    }

    @Override
    public void createMovie(String title, String genre, int length) {
        if (movieRepo.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("Movie with this title already exists.");
        }
        Movie movie = new Movie(title, genre, length);
        movieRepo.save(movie);
    }

    @Override
    public void updateMovie(String title, String category, int length) {
        Optional<Movie> optionalMovie = movieRepo.findByTitle(title);
        if (optionalMovie.isEmpty()) {
            throw new IllegalArgumentException("Movie not found.");
        }
        Movie movie = optionalMovie.get();
        movie.setCategory(category);
        movie.setLength(length);
        movieRepo.save(movie);
    }

    @Override
    public void deleteMovie(String title) {
        if (movieRepo.findByTitle(title).isEmpty()) {
            throw new IllegalArgumentException("Movie not found.");
        }
        movieRepo.delete(movieRepo.findByTitle(title).get());
    }

    @Override
    public List<MovieDto> listMovies() {
        List<Movie> movies = movieRepo.findAll();
        return movies.stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getCategory(), movie.getLength()))
                .toList();
    }

}

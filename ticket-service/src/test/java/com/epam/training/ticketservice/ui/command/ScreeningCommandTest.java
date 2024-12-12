package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.ui.commands.ScreeningCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreeningCommandTest {
    public static final String MOVIE_TITLE = "Test Movie";
    public static final String MOVIE_CATEGORY = "animation";
    public static final int MOVIE_LENGTH = 90;
    public static final String ROOM_NAME = "Test Room";
    public static final int ROOM_ROWS = 10;
    public static final int ROOM_COLS = 10;
    public static final String SCENING_TIME_STR = "2024-11-11 12:00";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Movie MOVIE = new Movie(MOVIE_TITLE, MOVIE_CATEGORY, MOVIE_LENGTH);
    Room ROOM = new Room(ROOM_NAME, ROOM_ROWS, ROOM_COLS);
    LocalDateTime TIME = LocalDateTime.parse(SCENING_TIME_STR, formatter);

    ScreeningDto SCREENING_DTO = new ScreeningDto(MOVIE, ROOM, TIME);

    List<ScreeningDto> screenings = List.of(SCREENING_DTO);

    ScreeningService screeningService = mock(ScreeningServiceImpl.class);
    MovieService movieService = mock(MovieServiceImpl.class);
    UserService userService = mock(UserServiceImpl.class);
    ScreeningCommand underTest = new ScreeningCommand(screeningService,userService,movieService);

    @Test
    void listScreeningsShouldShowNoScreeningsInitialy() {
        when(screeningService.listScreenings()).thenReturn(List.of());

        String result = underTest.listScreenings();

        assertEquals("There are no screenings", result);
        verify(screeningService).listScreenings();
    }

    @Test
    void listScreeningsTest2(){
        when(screeningService.listScreenings()).thenReturn(screenings);

        String result = underTest.listScreenings();

        assertEquals("Test Movie (animation, 90 minutes), screened in room Test Room, at 2024-11-11 12:00", result);
        verify(screeningService).listScreenings();
    }

    @Test
    void deleteScreeningTest(){
        underTest.deleteScreening(MOVIE_TITLE,ROOM_NAME,SCENING_TIME_STR);
        verify(screeningService).deleteScreening(MOVIE_TITLE,ROOM_NAME,TIME);
    }

    @Test
    void createScreeningTest(){
        when(screeningService.listScreenings()).thenReturn(List.of());

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,SCENING_TIME_STR);

        assertEquals("Screening created", result);
    }

    @Test
    void createScreeningTestOverlapping(){
        when(screeningService.listScreenings()).thenReturn(List.of(new ScreeningDto(MOVIE, ROOM, TIME)));
        when(movieService.listMovies()).thenReturn(List.of(new MovieDto(MOVIE_TITLE,MOVIE_CATEGORY, MOVIE_LENGTH)));

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,SCENING_TIME_STR);

        assertEquals("There is an overlapping screening", result);

    }

    @Test
    void createScreeningTestOverlapping2(){
        when(screeningService.listScreenings()).thenReturn(List.of(new ScreeningDto(MOVIE, ROOM, TIME)));
        when(movieService.listMovies()).thenReturn(List.of(new MovieDto(MOVIE_TITLE,MOVIE_CATEGORY, MOVIE_LENGTH)));

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,"2024-11-11 13:00");

        assertEquals("There is an overlapping screening", result);

    }

    @Test
    void createScreeningTestOverlapping3(){
        when(screeningService.listScreenings()).thenReturn(List.of(new ScreeningDto(MOVIE, ROOM, TIME)));
        when(movieService.listMovies()).thenReturn(List.of(new MovieDto(MOVIE_TITLE,MOVIE_CATEGORY, MOVIE_LENGTH)));

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,"2024-11-11 11:00");

        assertEquals("There is an overlapping screening", result);
    }

    @Test
    void createScreeningTestOverlapping4(){
        when(screeningService.listScreenings()).thenReturn(List.of(new ScreeningDto(MOVIE, ROOM, TIME)));
        when(movieService.listMovies()).thenReturn(List.of(new MovieDto(MOVIE_TITLE,MOVIE_CATEGORY, MOVIE_LENGTH)));

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,"2024-11-11 10:25");

        assertEquals("This would start in the break period after another screening in this room", result);

    }

    @Test
    void createScreeningTestOverlapping5(){
        when(screeningService.listScreenings()).thenReturn(List.of(new ScreeningDto(MOVIE, ROOM, TIME)));
        when(movieService.listMovies()).thenReturn(List.of(new MovieDto(MOVIE_TITLE,MOVIE_CATEGORY, MOVIE_LENGTH)));

        String result = underTest.createScreening(MOVIE_TITLE,ROOM_NAME,"2024-11-11 13:35");

        assertEquals("This would start in the break period after another screening in this room", result);

    }
}

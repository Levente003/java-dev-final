package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand {
    private final ScreeningService screeningService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final MovieService movieService;

    @ShellMethod(value = "Create a new screening", key = "create screening")
    public String createScreening(String movieTitle, String roomName, String startTime) {
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        boolean badTime = screeningService.listScreenings().stream()
                .anyMatch(
                        screeningDto ->
                                screeningDto.startTime().isBefore(startDateTime.plusMinutes(
                                        movieService.listMovies()
                                                .stream()
                                                .filter(movieDto -> Objects.equals(movieDto.title(), movieTitle))
                                                .findFirst().get().length()))
                                && screeningDto.startTime().plusMinutes(screeningDto.movie().getLength())
                                        .isAfter(startDateTime)
                                && screeningDto.room().getName().equals(roomName));
        boolean breakTime = screeningService.listScreenings().stream()
                .anyMatch(
                        screeningDto ->
                                screeningDto.startTime().isBefore(startDateTime.plusMinutes(
                                        movieService.listMovies()
                                                .stream()
                                                .filter(movieDto -> Objects.equals(movieDto.title(), movieTitle))
                                                .findFirst().get().length() + 10))
                && screeningDto.startTime().plusMinutes(screeningDto.movie().getLength() + 10)
                                        .isAfter(startDateTime)
                && screeningDto.room().getName().equals(roomName));
        if (badTime) {
            return "There is an overlapping screening";
        }
        if (breakTime) {
            return "This would start in the break period after another screening in this room";
        }
        screeningService.createScreening(movieTitle, roomName, startDateTime);
        return "Screening created";
    }

    @ShellMethod(value = "List all screenings", key = "list screenings")
    public String listScreenings() {
        List<ScreeningDto> screenings = screeningService.listScreenings();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder sb = new StringBuilder();
        screenings.forEach(screening ->
                sb.append("%s (%s, %d minutes), screened in room %s, at %s"
                        .formatted(screening.movie().getTitle(),
                                screening.movie().getCategory(),
                                screening.movie().getLength(),
                                screening.room().getName(),
                                screening.startTime().format(formatter))).append("\n"));
        return sb.toString().trim();
    }

    @ShellMethod(value = "Delete a screening", key = "delete screening")
    public String deleteScreening(String movieTitle, String roomName, String startTime) {
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        screeningService.deleteScreening(movieTitle, roomName, startDateTime);
        return "Screening deleted: " + movieTitle + " in room " + roomName + " at " + startTime;
    }
}

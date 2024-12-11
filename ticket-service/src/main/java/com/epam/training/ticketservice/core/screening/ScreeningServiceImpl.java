package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistance.Screening;
import com.epam.training.ticketservice.core.screening.persistance.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepo;
    private final MovieRepository movieRepo;
    private final RoomRepository roomRepo;


    @Override
    public List<ScreeningDto> listScreenings() {
        return screeningRepo.findAll().stream()
                .map(screening -> new ScreeningDto(movieRepo.findByTitle(screening.getMovieTitle()).get(), roomRepo.findByName(screening.getRoomName()).get(), screening.getStartTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ScreeningDto> createScreening(String movieTitle, String roomName, LocalDateTime startTime) {
        Screening screening = new Screening();
        screening.setMovieTitle(movieTitle);
        screening.setRoomName(roomName);
        screening.setStartTime(startTime);
        screeningRepo.save(screening);
        Optional<Movie> movie = movieRepo.findByTitle(screening.getMovieTitle());
        Optional<Room> room = roomRepo.findByName(screening.getRoomName());
        if (movie.isPresent() && room.isPresent()) {
            return Optional.of(new ScreeningDto(movie.get(), room.get(), startTime));
        }
        return Optional.empty();

    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime startTime) {
        Optional<Screening> screening = screeningRepo.findByMovieTitleAndRoomNameAndStartTime(movieTitle, roomName, startTime);
        screening.ifPresent(screeningRepo::delete);
    }
}

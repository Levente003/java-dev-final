package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.persistance.ScreeningRepository;

import static org.mockito.Mockito.mock;

public class ScreeningServiceTest {
    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningService underTest = new ScreeningServiceImpl(
            screeningRepository,
            movieRepository,
            roomRepository);


}

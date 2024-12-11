package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<ScreeningDto> listScreenings();

    Optional<ScreeningDto> createScreening(String movieTitle, String roomName, LocalDateTime startTime);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime startTime);

}

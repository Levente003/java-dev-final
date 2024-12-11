package com.epam.training.ticketservice.core.screening.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    Optional<Screening>
        findByMovieTitleAndRoomNameAndStartTime(String movieTitle, String roomName, LocalDateTime startTime);
}

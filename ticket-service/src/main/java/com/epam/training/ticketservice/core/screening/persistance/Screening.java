package com.epam.training.ticketservice.core.screening.persistance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Screenings")
@Data
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue
    private Long id;

    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;


    public Screening(String movieTitle, String roomName, LocalDateTime startTime) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startTime = startTime;
    }
}

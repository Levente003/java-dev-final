package com.epam.training.ticketservice.core.screening.persistance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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


}

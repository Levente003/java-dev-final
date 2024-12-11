package com.epam.training.ticketservice.core.room.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    private String name;
    private int rows;
    private int cols;
}

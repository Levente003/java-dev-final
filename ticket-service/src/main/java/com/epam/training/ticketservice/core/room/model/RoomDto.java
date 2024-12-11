package com.epam.training.ticketservice.core.room.model;

import lombok.Getter;


public record RoomDto(@Getter String name, int rows, int cols) {
    @Override
    public String toString() {
        return "Room "+ name +" with "+ rows* cols +" seats, "+ rows +" rows and "+ cols +" columns";
    }
}

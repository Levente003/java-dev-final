package com.epam.training.ticketservice.core.room.model;

public record RoomDto(String name, int rows, int cols) {
    @Override
    public String toString() {
        return "Room "+ name +" with "+ rows* cols +" seats, "+ rows +" rows and "+ cols +" columns";
    }
}

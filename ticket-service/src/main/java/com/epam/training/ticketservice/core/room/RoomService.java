package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> listRooms();

    RoomDto createRoom(String name, int rows, int columns);

    RoomDto updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);
}

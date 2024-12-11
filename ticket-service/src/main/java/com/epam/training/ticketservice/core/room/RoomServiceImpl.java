package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepo;

    @Override
    public List<RoomDto> listRooms() {
        return roomRepo.findAll().stream()
                .map(room -> new RoomDto(room.getName(), room.getRows(), room.getCols()))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto createRoom(String name, int rows, int cols) {
        Room room = new Room();
        room.setName(name);
        room.setRows(rows);
        room.setCols(cols);
        roomRepo.save(room);
        return new RoomDto(name, rows, cols);
    }

    @Override
    public RoomDto updateRoom(String name, int rows, int cols) {
        Optional<Room> optionalRoom = roomRepo.findByName(name);
        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Room not found.");
        }
        Room room = optionalRoom.get();
        room.setRows(rows);
        room.setCols(cols);
        roomRepo.save(room);
        return new RoomDto(name, rows, cols);
    }

    @Override
    public void deleteRoom(String name) {
        Optional<Room> room = roomRepo.findByName(name);
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room not found.");
        }
        roomRepo.delete(room.get());
    }
}

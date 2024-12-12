package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {
    private final RoomService roomService;
    private final UserService userService;

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Create a new room", key = "create room")
    public String createRoom(String name, int rows, int columns) {
        roomService.createRoom(name, rows, columns);
        return "Room created: " + name;
    }

    @ShellMethod(value = "List all rooms", key = "list rooms")
    public String listRooms() {
        List<RoomDto> rooms = roomService.listRooms();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        StringBuilder sb = new StringBuilder();
        rooms.forEach(room -> sb.append(room.toString()).append("\n"));
        return sb.toString().trim();
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Update a room", key = "update room")
    public String updateRoom(String name, int rows, int cols) {
        roomService.updateRoom(name, rows, cols);
        return "Room updated: " + name;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(value = "Delete a room",key = "delete room")
    public String deleteRoom(String name) {
        roomService.deleteRoom(name);
        return "Room deleted: " + name;
    }

    private Availability isAdmin() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}

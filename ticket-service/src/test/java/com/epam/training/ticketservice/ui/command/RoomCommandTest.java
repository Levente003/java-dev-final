package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.ui.commands.RoomCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomCommandTest {
    RoomDto roomDto = new RoomDto("Test Room",10,10);
    private RoomService roomService = mock(RoomServiceImpl.class);
    private UserService userService = mock(UserServiceImpl.class);
    private RoomCommand underTest = new RoomCommand(roomService,userService);

    @Test
    void listRoomsWhenThereAreNoRooms() {
        when(roomService.listRooms()).thenReturn(List.of());

        String result = underTest.listRooms();

        assertEquals("There are no rooms at the moment", result);
        verify(roomService).listRooms();
    }
    @Test
    void listRoomsWhenThereAreRooms() {
        when(roomService.listRooms()).thenReturn(List.of(roomDto));

        String result = underTest.listRooms();

        assertEquals("Room Test Room with 100 seats, 10 rows and 10 columns", result);
        verify(roomService).listRooms();
    }
}

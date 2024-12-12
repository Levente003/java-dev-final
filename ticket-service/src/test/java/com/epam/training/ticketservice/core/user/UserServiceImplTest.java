package com.epam.training.ticketservice.core.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class UserServiceImplTest {
    private final UserRepository userRepo = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepo);

    @Test
    void testPrivilegedLoginShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        Optional<User> expected = Optional.of(user);
        when(userRepo.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(user));

        // When
        Optional<UserDto> actual = underTest.loginAdmin("admin", "admin");

        // Then
        assertEquals(expected.get().getUsername(), actual.get().username());
        assertEquals(expected.get().getRole(), actual.get().role());
        verify(userRepo).findByUsernameAndPassword("admin", "admin");
    }

    @Test
    void testPrivilegedLoginShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDto> expected = Optional.empty();
        when(userRepo.findByUsernameAndPassword("dummy", "dummy")).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.loginAdmin("dummy", "dummy");

        // Then
        assertEquals(expected, actual);
        verify(userRepo).findByUsernameAndPassword("dummy", "dummy");
    }
}
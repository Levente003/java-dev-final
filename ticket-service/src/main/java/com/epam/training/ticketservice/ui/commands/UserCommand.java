package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "sign out", value = "User logout")
    public String signOut() {
        return userService.logout()
                .map(userDto -> userDto + " is logged out!")
                .orElse("You need to login first!");
    }


    @ShellMethod(key = "sign in privileged", value = "Admin user login")
    public String signInPrivileged(String username, String password) {
        return userService.loginAdmin(username, password)
                .map(userDto -> "Signed in with privileged account '" + userDto + "'")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        return userService.describe()
                .map(userDto -> userDto.role().equals(User.Role.ADMIN)
                        ? "Signed in with privileged account '" + userDto + "'"
                        : "Signed in with account '" + userDto + "'")
                .orElse("You are not signed in");
    }
}

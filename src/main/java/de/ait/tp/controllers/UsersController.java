package de.ait.tp.controllers;

import de.ait.tp.controllers.api.UsersApi;
import de.ait.tp.dto.NewUserDto;
import de.ait.tp.dto.UserDto;
import de.ait.tp.security.details.AuthenticatedUser;
import de.ait.tp.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController


public class UsersController implements UsersApi {
    private final UsersService usersService;

    @Override
    public UserDto register(NewUserDto newUser) {
        return usersService.register(newUser);
    }

    @Override
    public UserDto getConfirmation(String confirmCode){
        return usersService.confirm(confirmCode);
    }

    @Override
    public UserDto getProfile(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        return usersService.getUserById(currentUserId);
    }

}

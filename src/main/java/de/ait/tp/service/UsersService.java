package de.ait.tp.service;


import de.ait.tp.dto.NewUserDto;
import de.ait.tp.dto.StandardResponseDto;
import de.ait.tp.dto.UserDto;

import java.util.List;

public interface UsersService {
    UserDto register(NewUserDto newUser);

    UserDto getUserById(Long currentUserId);

    UserDto confirm(String confirmCode);
}



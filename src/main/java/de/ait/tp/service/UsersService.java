package de.ait.tp.service;

import de.ait.tp.dto.NewUserDto;
import de.ait.tp.dto.Pagination;
import de.ait.tp.dto.UpdateUserDto;
import de.ait.tp.dto.UserDto;

import java.util.List;

public interface UsersService {
    UserDto register(NewUserDto newUser);
    UserDto getUserById(Long currentUserId);
    UserDto confirm(String confirmCode);
   // List<UserDto> getAllUsers();
    Pagination getAllUsers(int page, int size, String orderBy, Boolean desc);
    UserDto updateUser(Long userId, UpdateUserDto updateUser);
    UserDto deleteUser(Long userId);
}



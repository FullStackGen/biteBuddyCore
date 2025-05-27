package com.bite.buddy.service;

import com.bite.buddy.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto user);

    UserDto updateUser(UserDto user, String userId);

    UserDto getUser(String userId);

    List<UserDto> getAllUsers();

    void deleteUser(String userId);
}

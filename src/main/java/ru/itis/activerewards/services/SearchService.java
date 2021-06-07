package ru.itis.activerewards.services;

import ru.itis.activerewards.dto.UserDto;

import java.util.List;


public interface SearchService {
    List<UserDto> getAllUsers();
}

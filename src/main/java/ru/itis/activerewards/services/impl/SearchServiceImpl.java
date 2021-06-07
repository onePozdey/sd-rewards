package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.activerewards.dto.UserDto;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.SearchService;

import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }
}

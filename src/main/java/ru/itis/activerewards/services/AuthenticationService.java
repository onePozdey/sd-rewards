package ru.itis.activerewards.services;

import org.springframework.security.core.Authentication;
import ru.itis.activerewards.dto.UserDto;
import ru.itis.activerewards.models.User;


public interface AuthenticationService {
    UserDto getUserById(Long id);

    UserDto getUserByAuthentication(Authentication authentication);

    User getUserModelByAuthentication(Authentication authentication);
}

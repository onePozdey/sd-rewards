package ru.itis.activerewards.services.impl;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.itis.activerewards.dto.UserDto;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.security.details.UserDetailsImpl;
import ru.itis.activerewards.services.AuthenticationService;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDto getUserById(Long id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserDto.from(user);
    }

    @Override
    public UserDto getUserByAuthentication(Authentication authentication) {
        long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserDto.from(user);
    }

    @Override
    public User getUserModelByAuthentication(Authentication authentication) {
        long userId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        if (user instanceof HibernateProxy) {
//            user = (User) ((HibernateProxy) user).getHibernateLazyInitializer().getImplementation();
//        }
        return user;
    }
}

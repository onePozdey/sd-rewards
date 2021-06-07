package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.activerewards.forms.NewUserForm;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.security.role.Role;
import ru.itis.activerewards.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void registerUser(NewUserForm form) {
        User user = User.builder()
                .login(form.getUsername().toLowerCase())
                .passwordHash(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
                .state(User.UserState.ACTIVE)
                .build();
        usersRepository.save(user);
    }

    @Override
    public User createUser(String login, String password) {
        User user = User.builder()
                .login(login)
                .passwordHash(passwordEncoder.encode(password))
                .role(Role.USER)
                .state(User.UserState.ACTIVE)
                .balance(0D)
                .giftBalance(0D)
                .build();
        usersRepository.save(user);
        return user;
    }

    @Override
    public User createUser(String login, Role role, User.UserState state) {
        User user = User.builder()
                .login(login)
                .role(role)
                .state(state)
                .balance(0D)
                .giftBalance(0D)
                .build();
        usersRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void updateRoleAndState(String login, Role role, User.UserState state) {
        usersRepository.updateRoleByLogin(login, role, state);
    }

    @Override
    public void updatePassword(User user, String password) {
        String passwordHash = passwordEncoder.encode(password);
        if (user.getPasswordHash() == null || !user.getPasswordHash().equals(passwordHash)) {
            user.setPasswordHash(passwordHash);
            usersRepository.save(user);
        }
    }
}

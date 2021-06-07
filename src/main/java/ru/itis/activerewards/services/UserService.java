package ru.itis.activerewards.services;

import ru.itis.activerewards.forms.NewUserForm;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.security.role.Role;

public interface UserService {
    void registerUser(NewUserForm form);

    User createUser(String login, String password);

    User createUser(String login, Role role, User.UserState state);

    void updateRoleAndState(String login, Role role, User.UserState state);

    void updatePassword(User user, String password);

}

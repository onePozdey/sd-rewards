package ru.itis.activerewards.services;


public interface LDAPService {
    boolean isAuthenticated(String login, String password);

    void uploadUsers();
}

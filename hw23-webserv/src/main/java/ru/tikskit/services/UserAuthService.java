package ru.tikskit.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}

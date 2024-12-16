package ru.otus.dzone.service;

import ru.otus.dzone.entity.User;

public interface UserService {
    User createUser(String username, String email, int age, String password);
    User getUserByUsername(String username);
}

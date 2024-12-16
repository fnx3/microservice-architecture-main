package ru.otus.dzone.service;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import java.util.Map;

public interface AuthService {

    Long registerUser(String username, String email, int age, String password);

    Cookie loginUser(String username, String password, Cookie[] cookies) throws AuthenticationException;

    Map<String, String> auth(Cookie[] cookies) throws AuthenticationException;

    void logout(Cookie[] cookies);
}

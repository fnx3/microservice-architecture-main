package ru.otus.userapp.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    String getAuthUserId(HttpServletRequest httpServletRequest);
}

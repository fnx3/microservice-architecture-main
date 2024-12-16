package ru.otus.mailservice.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    String getAuthUserId(HttpServletRequest httpServletRequest);
}

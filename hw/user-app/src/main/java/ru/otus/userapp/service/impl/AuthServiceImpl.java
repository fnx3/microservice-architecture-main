package ru.otus.userapp.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.userapp.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String getAuthUserId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("X-UserId");
    }
}

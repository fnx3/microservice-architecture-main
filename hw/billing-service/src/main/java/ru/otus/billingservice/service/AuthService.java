package ru.otus.billingservice.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    String getAuthUserId(HttpServletRequest httpServletRequest);
}

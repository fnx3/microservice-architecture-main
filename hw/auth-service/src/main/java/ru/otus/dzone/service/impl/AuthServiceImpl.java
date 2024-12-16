package ru.otus.dzone.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.swagger.v3.oas.models.headers.Header;


import lombok.AllArgsConstructor;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.dzone.entity.CacheUserDetails;
import ru.otus.dzone.entity.User;
import ru.otus.dzone.service.AuthService;
import ru.otus.dzone.service.UserService;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import java.util.*;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String AUTH_COOKIE_NAME = "session_id";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Cache<String, CacheUserDetails> sessionCache;

    @Override
    public Long registerUser(String username, String email, int age, String password) {
        return userService.createUser(username, email, age, passwordEncoder.encode(password)).getId();
    }

    @Override
    public Cookie loginUser(String username, String password, Cookie[] cookies) throws AuthenticationException {
        Cookie authCookie = getAuthCookieIfPresent(cookies);
        if (authCookie == null) {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                    throw new AuthenticationException(String.format("user with username '%s' doesnt exist", username));
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthenticationException("wrong password");
            }
            String authCookieValue = UUID.randomUUID().toString();
            sessionCache.put(authCookieValue, CacheUserDetails.fromDomain(user));
            return new Cookie(AUTH_COOKIE_NAME, authCookieValue);
        }
        return authCookie;
    }

    private Cookie getAuthCookieIfPresent(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        Cookie cookie = Arrays.stream(cookies).filter(sessionCookie -> AUTH_COOKIE_NAME.equals(sessionCookie.getName()))
                .findFirst()
                .orElse(null);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            if (sessionCache.getIfPresent(sessionId) != null) {
                return cookie;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> auth(Cookie[] cookies) throws AuthenticationException {
        Cookie authCookie = getAuthCookieIfPresent(cookies);
        Map<String, String> headers = new HashMap<>();
        if (authCookie != null) {
            CacheUserDetails userDetails = sessionCache.getIfPresent(authCookie.getValue());
            if (userDetails != null){
                headers.put("X-UserId", String.valueOf(userDetails.getId()));
                headers.put("X-User", userDetails.getUsername());
                headers.put("X-Email", userDetails.getEmail());
                headers.put("X-Age", String.valueOf(userDetails.getAge()));
                return headers;
            }
        }
        throw new AuthenticationException("session expired");
    }

    @Override
    public void logout(Cookie[] cookies) {
        Cookie authCookie = getAuthCookieIfPresent(cookies);
        if (authCookie != null){
            sessionCache.asMap().remove(authCookie.getValue());
        }
    }

}

package ru.otus.dzone.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.dzone.service.AuthService;
import ru.otus.dzone.web.dto.LoginUserRequest;
import ru.otus.dzone.web.dto.RegisterUserRequest;
import ru.otus.dzone.web.dto.RegisterUserResponse;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String SIGNIN = "/signin";
    private static final String AUTH = "/auth";
    private static final String LOGOUT = "/logout";

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterUserResponse> register(@Validated @RequestBody RegisterUserRequest request) {
        return new ResponseEntity<>(new RegisterUserResponse(authService.registerUser(request.getUsername(), request.getEmail(), request.getAge(), request.getPassword())), HttpStatus.CREATED);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Validated @RequestBody LoginUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        Cookie authCookie = authService.loginUser(request.getUsername(), request.getPassword(), httpServletRequest.getCookies());
        httpServletResponse.addCookie(authCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(SIGNIN)
    public ResponseEntity<String> signin() {
        return new ResponseEntity<>("Please go to login and provide Login/Password", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(AUTH)
    public ResponseEntity<?> auth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        Map<String, String> headers = authService.auth(httpServletRequest.getCookies());
        headers.forEach(httpServletResponse::addHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        authService.logout(httpServletRequest.getCookies());
        httpServletResponse.reset();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

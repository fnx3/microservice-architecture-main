package ru.otus.userapp.service;

import ru.otus.userapp.entity.UserProfile;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

public interface UserProfileService {

    Long createUserProfile(String avatarUrl, String nickname, HttpServletRequest request) throws AuthenticationException;

    UserProfile getUserProfile(HttpServletRequest httpServletRequest) throws AuthenticationException;

    UserProfile updateUserProfile(String avatarUrl, String nickname, HttpServletRequest httpServletRequest) throws AuthenticationException;
}

package ru.otus.userapp.web.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.userapp.service.UserProfileService;
import ru.otus.userapp.web.dto.CreateUserProfileRequest;
import ru.otus.userapp.web.dto.UpdateUserProfileRequest;
import ru.otus.userapp.web.dto.UserProfileResponse;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<Long> createProfile(@Validated @RequestBody CreateUserProfileRequest request, HttpServletRequest httpServletRequest) throws AuthenticationException {
        return new ResponseEntity<>(userProfileService.createUserProfile(request.getAvatarUrl(), request.getNickname(), httpServletRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(HttpServletRequest httpServletRequest) throws AuthenticationException {
        return ResponseEntity.ok(UserProfileResponse.fromDomain(userProfileService.getUserProfile(httpServletRequest), httpServletRequest));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(@Validated @RequestBody UpdateUserProfileRequest request, HttpServletRequest httpServletRequest) throws AuthenticationException {
        return ResponseEntity.ok(UserProfileResponse.fromDomain(userProfileService.updateUserProfile(request.getAvatarUrl(), request.getNickname(), httpServletRequest), httpServletRequest));
    }
}

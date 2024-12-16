package ru.otus.userapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.common.dto.CreateWalletResponse;
import ru.otus.userapp.entity.UserProfile;
import ru.otus.userapp.repository.UserProfileRepository;
import ru.otus.userapp.service.AuthService;
import ru.otus.userapp.service.RestClient;
import ru.otus.userapp.service.UserProfileService;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final AuthService authService;
    private final RestClient restClient;
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public Long createUserProfile(String avatarUrl, String nickname, HttpServletRequest request) throws AuthenticationException {
        String authUserId = authService.getAuthUserId(request);
        if (authUserId != null) {
            CreateWalletResponse userWallet = restClient.createUserWallet(authUserId);

            Long profileId = userProfileRepository.save(UserProfile.builder()
                            .userId(authUserId)
                            .avatarUrl(avatarUrl)
                            .nickname(nickname)
                            .walletId(userWallet.getWalletId())
                            .build())
                    .getId();

            if (userWallet.getWalletId() == null){
                throw new IllegalStateException("Profile not created");
            }

            return profileId;
        }
        throw new AuthenticationException("You must be authenticated");
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile getUserProfile(HttpServletRequest request) throws AuthenticationException {
        String authUserId = authService.getAuthUserId(request);
        if (authUserId != null) {
            UserProfile userProfile = userProfileRepository.getUserProfileByUserId(authUserId);
            if (userProfile == null) {
                throw new EntityNotFoundException(String.format("user with id '%s' doesnt have profile", authUserId));
            }
            return userProfile;
        }
        throw new AuthenticationException("You must be authenticated");
    }

    @Override
    public UserProfile updateUserProfile(String avatarUrl, String nickname, HttpServletRequest request) throws AuthenticationException {
        String authUserId = authService.getAuthUserId(request);
        if (authUserId != null) {
            UserProfile userProfile = userProfileRepository.getUserProfileByUserId(authUserId);
            if (userProfile == null) {
                throw new EntityNotFoundException(String.format("user with id '%s' doesnt have profile", authUserId));
            }
            userProfile.setAvatarUrl(avatarUrl);
            userProfile.setNickname(nickname);
            return userProfileRepository.save(userProfile);
        }
        throw new AuthenticationException("You must be authenticated");
    }
}

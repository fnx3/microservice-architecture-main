package ru.otus.userapp.web.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.userapp.entity.UserProfile;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
public class UserProfileResponse {
    private String userId;
    private String username;
    private String email;
    private String nickname;
    private String avatarUrl;
    private int age;
    private String walletId;

    public static UserProfileResponse fromDomain(UserProfile userProfile, HttpServletRequest request){
        return UserProfileResponse.builder()
                .userId(userProfile.getUserId())
                .age(Integer.parseInt(request.getHeader("X-Age")))
                .avatarUrl(userProfile.getAvatarUrl())
                .email(request.getHeader("X-Email"))
                .nickname(userProfile.getNickname())
                .username(request.getHeader("X-User"))
                .walletId(userProfile.getWalletId())
                .build();
    }
}

package ru.otus.userapp.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserProfileRequest {
    @NotEmpty
    private String avatarUrl;
    @NotEmpty
    private String nickname;
}

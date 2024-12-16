package ru.otus.userapp.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateUserProfileRequest {

    private String avatarUrl;

    private String nickname;
}

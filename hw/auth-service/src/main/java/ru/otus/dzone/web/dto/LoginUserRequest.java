package ru.otus.dzone.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}

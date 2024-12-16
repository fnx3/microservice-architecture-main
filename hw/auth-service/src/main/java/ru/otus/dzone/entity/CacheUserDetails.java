package ru.otus.dzone.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CacheUserDetails {
    private Long id;
    private String username;
    private String email;
    private int age;

    public static CacheUserDetails fromDomain(User user) {
        return CacheUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .build();
    }
}

package ru.otus.userapp.entity;

import lombok.*;

import javax.persistence.*;

@Table
@Entity(name = "user_profile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String nickname;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "wallet_id", unique = true)
    private String walletId;
}

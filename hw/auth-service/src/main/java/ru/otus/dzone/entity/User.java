package ru.otus.dzone.entity;


import lombok.*;

import javax.persistence.*;

@Table
@Entity(name = "auth_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private int age;
}

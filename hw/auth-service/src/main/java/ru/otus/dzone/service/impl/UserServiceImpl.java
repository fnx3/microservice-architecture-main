package ru.otus.dzone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dzone.entity.User;
import ru.otus.dzone.repository.UserRepository;
import ru.otus.dzone.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(String username, String email, int age, String password) {
        return userRepository.save(User.builder()
                .username(username)
                .email(email)
                .age(age)
                .password(password)
                .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}

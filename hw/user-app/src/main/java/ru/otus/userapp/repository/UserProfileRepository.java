package ru.otus.userapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.userapp.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile getUserProfileByUserId(String userId);

    @Override
    boolean existsById(Long id);
}

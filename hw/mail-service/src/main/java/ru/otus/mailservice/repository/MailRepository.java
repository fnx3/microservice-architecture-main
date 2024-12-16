package ru.otus.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.mailservice.entity.Mail;

import java.util.List;
import java.util.UUID;

@Repository
public interface MailRepository extends JpaRepository<Mail, UUID> {

    List<Mail> getAllByUserId(String userId);
}

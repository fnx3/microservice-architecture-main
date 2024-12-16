package ru.otus.mailservice.service;

import ru.otus.common.entity.orders.message.MailMessage;
import ru.otus.mailservice.entity.Mail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MailService {
    void saveMail(MailMessage message);

    List<Mail> getUserMails(HttpServletRequest request);
}

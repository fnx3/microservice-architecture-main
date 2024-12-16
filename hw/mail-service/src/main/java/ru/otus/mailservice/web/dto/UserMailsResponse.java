package ru.otus.mailservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.mailservice.entity.Mail;

import java.util.List;

@Data
@AllArgsConstructor
public class UserMailsResponse {
    private List<UserMail> mails;

    public static UserMailsResponse fromDomain(List<Mail> userMails) {
        List<UserMail> mailList = userMails.stream()
                .map(userMail -> new UserMail(userMail.getUserId(), userMail.getOrderId(), userMail.getContent(), userMail.getPaymentStatus()))
                .toList();

        return new UserMailsResponse(mailList);
    }
}

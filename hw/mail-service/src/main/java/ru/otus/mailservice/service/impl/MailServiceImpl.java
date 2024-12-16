package ru.otus.mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.MailMessage;
import ru.otus.mailservice.entity.Mail;
import ru.otus.mailservice.repository.MailRepository;
import ru.otus.mailservice.service.AuthService;
import ru.otus.mailservice.service.MailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final AuthService authService;

    @Override
    public void saveMail(MailMessage message) {
        mailRepository.save(Mail.builder()
                .userId(message.getUserId())
                .orderId(message.getOrderId())
                .paymentStatus(message.getPaymentStatus())
                .content("Сообщение об оплате")
                .build());

        log.info("saved mail with order id: {}", message.getOrderId());
    }

    @Override
    public List<Mail> getUserMails(HttpServletRequest request) {
        String authUserId = authService.getAuthUserId(request);
        if (authUserId != null) {
            return mailRepository.getAllByUserId(authUserId);
        }

        return Collections.emptyList();
    }
}

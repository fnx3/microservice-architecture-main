package ru.otus.mailservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.MailMessage;
import ru.otus.common.entity.orders.message.OrderMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailConsumer {

    private final MailService mailService;

    @KafkaListener(topics = "mail",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(MailMessage message) {
        log.info("received message from payment topic with order id {}", message.getOrderId());
        mailService.saveMail(message);
    }
}

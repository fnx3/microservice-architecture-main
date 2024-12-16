package ru.otus.billingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.otus.common.entity.orders.message.MailMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMailProducer {

    public static final String MAIL_TOPIC = "mail";
    private final KafkaTemplate<String, MailMessage> kafkaTemplate;

    public void send(MailMessage message) {
        sendWithRetry(message, 5);
    }

    private void sendWithRetry(MailMessage message, int maxRetries) {
        ListenableFuture<SendResult<String, MailMessage>> future = kafkaTemplate.send(MAIL_TOPIC, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Mail message with id '{}' failed to push on mail topic: {}", message.getOrderId(), ex.getMessage());
                if (maxRetries > 0) {
                    log.info("Retrying message with id '{}'. {} retries left.", message.getOrderId(), maxRetries);
                    sendWithRetry(message, maxRetries - 1);
                } else {
                    log.error("Failed to send message with id '{}' after all retries.", message.getOrderId());
                }
            }

            @Override
            public void onSuccess(SendResult<String, MailMessage> result) {
                log.info("Mail message with id '{}' successfully pushed on mail topic", message.getOrderId());
            }
        });
    }
}

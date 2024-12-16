package ru.otus.billingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.OrderMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "payment",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderMessage message) {
        log.info("received message from orders topic with order id {}", message.getOrderId());
        paymentService.createPayment(message);
    }
}

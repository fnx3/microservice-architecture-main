package ru.otus.userapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.OrderMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "orders",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderMessage message) {
        log.info("received message with order id {}", message.getOrderId());
        orderService.updateOrderStatus(message, message.getStatus(), message.getOrderId());
    }
}

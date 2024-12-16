package ru.otus.userapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.message.OrderMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    public static final String PAYMENT_TOPIC = "payment";
    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;
    private final OrderService orderService;

    public void send(OrderMessage message) {
        sendWithRetry(message, 5);
    }

    private void sendWithRetry(OrderMessage message, int maxRetries) {
        ListenableFuture<SendResult<String, OrderMessage>> future = kafkaTemplate.send(PAYMENT_TOPIC, message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Order message with id '{}' failed to push on payment topic: {}", message.getOrderId(), ex.getMessage());
                if (maxRetries > 0) {
                    log.info("Retrying message with id '{}'. {} retries left.", message.getOrderId(), maxRetries);
                    sendWithRetry(message, maxRetries - 1);
                } else {
                    log.error("Failed to send message with id '{}' after all retries.", message.getOrderId());
                    orderService.updateOrderStatus(message, OrderStatus.ORDER_REJECTED, message.getOrderId());
                }
            }

            @Override
            public void onSuccess(SendResult<String, OrderMessage> result) {
                log.info("Order message with id '{}' successfully pushed on payment topic", message.getOrderId());
                orderService.updateOrderStatus(message, OrderStatus.WAITING_FOR_PAYMENT, message.getOrderId());
            }
        });
    }

}


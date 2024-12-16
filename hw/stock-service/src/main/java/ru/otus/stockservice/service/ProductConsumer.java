package ru.otus.stockservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.OrderMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductConsumer {

    private final StockService stockService;

    @KafkaListener(topics = "stock",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderMessage message) {
        log.info("received message from orders topic with order id {}", message.getOrderId());
        stockService.takeProductsFromStock(message);
    }
}

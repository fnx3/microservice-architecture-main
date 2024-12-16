package ru.otus.userapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import ru.otus.common.entity.orders.message.OrderMessage;
import ru.otus.userapp.entity.OrderEvent;

@Service
@RequiredArgsConstructor
public class OrderEventListener implements ApplicationListener<OrderEvent> {

    private final OrderProducer orderProducer;

    @Override
    public void onApplicationEvent(OrderEvent event) {
        OrderMessage orderMessage = (OrderMessage) event.getSource();
        orderProducer.send(orderMessage);
    }
}

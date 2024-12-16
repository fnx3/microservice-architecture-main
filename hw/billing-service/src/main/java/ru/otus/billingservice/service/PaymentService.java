package ru.otus.billingservice.service;

import ru.otus.common.entity.orders.message.MailMessage;
import ru.otus.common.entity.orders.message.OrderMessage;

public interface PaymentService {
    void createPayment(OrderMessage orderMessage);
    void refundOrder(OrderMessage orderMessage, MailMessage mailMessage);
}

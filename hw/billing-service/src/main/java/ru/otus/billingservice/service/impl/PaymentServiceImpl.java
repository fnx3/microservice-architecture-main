package ru.otus.billingservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.billingservice.entity.Payment;
import ru.otus.billingservice.entity.PaymentStatus;
import ru.otus.billingservice.entity.UserWallet;
import ru.otus.billingservice.repository.PaymentRepository;
import ru.otus.billingservice.repository.WalletRepository;
import ru.otus.billingservice.service.PaymentMailProducer;
import ru.otus.billingservice.service.PaymentService;
import ru.otus.billingservice.service.PaymentOrderProducer;
import ru.otus.billingservice.service.StockProducer;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.message.MailMessage;
import ru.otus.common.entity.orders.message.OrderMessage;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final WalletRepository walletRepository;
    private final PaymentOrderProducer paymentOrderProducer;
    private final PaymentMailProducer paymentMailProducer;
    private final StockProducer stockProducer;

    @Override
    public void createPayment(OrderMessage orderMessage) {
        MailMessage mailMessage = MailMessage.builder()
                .orderId(orderMessage.getOrderId())
                .userId(orderMessage.getUserId())
                .build();
        try {
            if (OrderStatus.ORDER_REFUND.equals(orderMessage.getStatus())) {
                refundOrder(orderMessage, mailMessage);
            } else {
                Payment payment = Payment.builder()
                        .userId(orderMessage.getUserId())
                        .paymentMethod(orderMessage.getPaymentMethod())
                        .amount(orderMessage.getPrice())
                        .orderId(orderMessage.getOrderId().toString())
                        .status(PaymentStatus.SUCCESS)
                        .build();

                if (PaymentMethod.CARD.equals(orderMessage.getPaymentMethod())) {
                    Optional<UserWallet> walletOptional = walletRepository.getByUserId(orderMessage.getUserId());
                    UserWallet wallet;
                    if (walletOptional.isPresent()) {
                        wallet = walletOptional.get();
                    } else {
                        throw new IllegalStateException("user with id " + orderMessage.getUserId() + " does not have wallet");
                    }
                    BigDecimal subtractPrice = wallet.getAmount().subtract(orderMessage.getPrice());

                    if (subtractPrice.doubleValue() >= 0) {
                        walletRepository.updateWalletAmout(subtractPrice, orderMessage.getUserId());
                        paymentRepository.save(payment);
                        orderMessage.setStatus(OrderStatus.ORDER_PAYED);
                        paymentOrderProducer.send(orderMessage);
                        stockProducer.send(orderMessage);
                        mailMessage.setPaymentStatus(PaymentStatus.SUCCESS.name());
                        paymentMailProducer.send(mailMessage);
                    } else {
                        payment.setStatus(PaymentStatus.ABORT);
                        paymentRepository.save(payment);
                        orderMessage.setStatus(OrderStatus.PAYMENT_ERROR);
                        paymentOrderProducer.send(orderMessage);
                        mailMessage.setPaymentStatus(PaymentStatus.ABORT.name());
                        paymentMailProducer.send(mailMessage);
                    }
                } else {
                    paymentRepository.save(payment);
                    orderMessage.setStatus(OrderStatus.ORDER_PAYED);
                    paymentOrderProducer.send(orderMessage);
                    stockProducer.send(orderMessage);
                    mailMessage.setPaymentStatus(PaymentStatus.SUCCESS.name());
                    paymentMailProducer.send(mailMessage);
                }
            }
        } catch (Exception exception) {
            log.error("payment error: " + exception.getMessage());
            orderMessage.setStatus(OrderStatus.PAYMENT_ERROR);
            mailMessage.setPaymentStatus(PaymentStatus.ABORT.name());
            paymentOrderProducer.send(orderMessage);
            paymentMailProducer.send(mailMessage);
        }
    }

    @Override
    public void refundOrder(OrderMessage orderMessage, MailMessage mailMessage) {
        walletRepository.addToWallet(orderMessage.getPrice(), orderMessage.getUserId());
        orderMessage.setStatus(OrderStatus.ORDER_REFUNDED);
        paymentOrderProducer.send(orderMessage);
        mailMessage.setPaymentStatus("REFUNDED");
        paymentMailProducer.send(mailMessage);
    }
}

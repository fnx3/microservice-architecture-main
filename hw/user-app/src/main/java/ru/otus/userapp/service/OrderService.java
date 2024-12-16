package ru.otus.userapp.service;

import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.Product;
import ru.otus.common.entity.orders.message.OrderMessage;
import ru.otus.userapp.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(UUID orderId, PaymentMethod paymentMethod, String deliveryLocation, List<Product> products, BigDecimal price, HttpServletRequest request);
    List<Order> getUserOrders(HttpServletRequest request);
    void updateOrderStatus(OrderMessage orderMessage, OrderStatus status, UUID orderId);
}

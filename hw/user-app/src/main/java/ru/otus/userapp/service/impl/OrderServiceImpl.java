package ru.otus.userapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.Product;
import ru.otus.common.entity.orders.message.OrderMessage;
import ru.otus.userapp.entity.Order;
import ru.otus.userapp.entity.OrderEvent;
import ru.otus.userapp.entity.OrderProduct;
import ru.otus.userapp.repository.OrderRepository;
import ru.otus.userapp.service.AuthService;
import ru.otus.userapp.service.OrderProducer;
import ru.otus.userapp.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UUID createOrder(UUID orderId, PaymentMethod paymentMethod, String deliveryLocation, List<Product> products, BigDecimal price, HttpServletRequest request) {
        String authUserId = authService.getAuthUserId(request);
        boolean orderExists = orderRepository.existsById(orderId);
        if (!orderExists) {
        Order order = Order.builder()
                .userId(authUserId)
                .id(orderId)
                .price(price)
                .deliveryLocation(deliveryLocation)
                .paymentMethod(paymentMethod)
                .status(OrderStatus.ORDER_CREATED)
                .build();

        List<OrderProduct> orderProducts = products.stream()
                .map(product -> OrderProduct.builder()
                        .order(order)
                        .productId(product.getId())
                        .quantity(product.getQuantity())
                        .build()).toList();

        order.setProducts(orderProducts);

        orderRepository.save(order);

        OrderEvent orderEvent = new OrderEvent(
                OrderMessage.builder()
                        .userId(authUserId)
                        .status(OrderStatus.ORDER_CREATED)
                        .deliveryLocation(deliveryLocation)
                        .paymentMethod(paymentMethod)
                        .orderId(orderId)
                        .price(price)
                        .products(products)
                        .build()
        );

        eventPublisher.publishEvent(orderEvent);
    }

        return orderId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getUserOrders(HttpServletRequest request) {
        String authUserId = authService.getAuthUserId(request);

        return orderRepository.findOrdersByUserId(authUserId);
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderMessage orderMessage, OrderStatus status, UUID orderId) {
        switch (status){
            case ORDER_PAYED, ORDER_REFUND, ORDER_REFUNDED, ORDER_REJECTED, ORDER_CREATED, WAITING_FOR_PAYMENT, DONE, PAYMENT_ERROR -> orderRepository.updateOrderStatus(status, orderId);
            case ORDER_COLLECTED -> orderRepository.updateOrderStatus(OrderStatus.DONE, orderId);
            case STOCK_ERROR -> {
                orderRepository.updateOrderStatus(OrderStatus.ORDER_REFUND, orderId);
                orderMessage.setStatus(OrderStatus.ORDER_REFUND);
                eventPublisher.publishEvent(new OrderEvent(orderMessage));
            }
        }

    }
}

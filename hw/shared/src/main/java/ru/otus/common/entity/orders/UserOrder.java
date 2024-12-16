package ru.otus.common.entity.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UserOrder {
    private UUID orderId;
    private String userId;
    private BigDecimal price;
    private List<UUID> productsId;
    private String deliveryLocation;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
}

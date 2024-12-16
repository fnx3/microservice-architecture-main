package ru.otus.common.entity.orders.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMessage implements Serializable {
    private UUID orderId;
    private String userId;
    private BigDecimal price;
    private List<Product> products;
    private String deliveryLocation;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
}

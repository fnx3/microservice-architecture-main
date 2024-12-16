package ru.otus.userapp.entity;

import lombok.*;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.Product;
import ru.otus.common.entity.orders.UserOrder;
import ru.otus.userapp.repository.UuidConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table
@Entity(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @Convert(converter = UuidConverter.class)
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products;

    public static UserOrder fromDomain(Order order){
        return UserOrder.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .userId(order.getUserId())
                .paymentMethod(order.getPaymentMethod())
                .deliveryLocation(order.getDeliveryLocation())
                .price(order.getPrice())
                .productsId(order.getProducts().stream().map(OrderProduct::getProductId).toList())
                .build();
    }
}

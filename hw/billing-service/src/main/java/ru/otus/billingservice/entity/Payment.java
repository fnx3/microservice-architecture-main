package ru.otus.billingservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.otus.common.entity.orders.PaymentMethod;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table
@Entity(name = "payment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "order_id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
}
